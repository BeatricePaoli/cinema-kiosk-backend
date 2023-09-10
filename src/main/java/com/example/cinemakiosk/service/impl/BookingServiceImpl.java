package com.example.cinemakiosk.service.impl;

import com.example.cinemakiosk.dto.*;
import com.example.cinemakiosk.dto.contextbroker.SmartBandCompactDto;
import com.example.cinemakiosk.model.*;
import com.example.cinemakiosk.repository.*;
import com.example.cinemakiosk.service.*;
import com.example.cinemakiosk.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BookingServiceImpl implements BookingService {

    @Value("${base-url}")
    private String baseUrl;

    @Value("${app.booking.limit-hours}")
    private Integer limitHours;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SmartBandRepository smartBandRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private ContextBrokerService contextBrokerService;

    @Autowired
    private DeviceActivityService deviceActivityService;

    @Override
    public Long createBooking(BookingDto dto) {
        Optional<Show> showOpt = showRepository.findById(dto.getShowId());
        if (showOpt.isPresent()) {
            Show show = showOpt.get();
            String[] splittedTime = show.getStartTime().split(":");

            if (splittedTime.length == 2) {
                LocalDate showDate = DateUtils.convertToLocalDate(show.getDate());
                LocalTime showTime = LocalTime.of(Integer.parseInt(splittedTime[0]), Integer.parseInt(splittedTime[1]));
                LocalDateTime limit = LocalDateTime.of(showDate, showTime).plusHours(limitHours);
                LocalDateTime now = LocalDateTime.now();

                if (!now.isAfter(limit)) {
                    List<Seat> allSeats = seatRepository.findByScreen(show.getScreen().getId());

                    List<SeatTakenDto> seatsTaken = bookingRepository.getSeatsTaken(Collections.singletonList(dto.getShowId()));
                    List<Long> idsTaken = seatsTaken.stream().map(SeatTakenDto::getSeatId).toList();

                    List<String> toBookLabels = dto.getSeats().stream().map(SeatDto::getLabel).collect(Collectors.toList());
                    List<Seat> toBookEntities = allSeats.stream()
                            .filter(seat -> toBookLabels.contains(seat.getLabel()) && !idsTaken.contains(seat.getId()))
                            .toList();

                    if (!toBookLabels.isEmpty() && toBookLabels.size() == toBookEntities.size()) {

                        User user = null;
                        if (dto.getUsername() != null) {
                            user = userService.getOrCreateByUsername(dto.getUsername());
                            if (user == null) {
                                log.error("User {} not found", dto.getUsername());
                                return null;
                            }
                        }

                        Booking booking = new Booking();
                        booking.setDateCreated(new Date());
                        booking.setStatus(BookingStatus.CREATED);
                        booking.setPrice(dto.getPrice());
                        booking.setShow(show);
                        booking.setSeats(new HashSet<>(toBookEntities));
                        booking.setUser(user);

                        booking = bookingRepository.save(booking);
                        return booking.getId();
                    } else {
                        log.error("Can't book seats {}, they are already taken", toBookLabels);
                        return null;
                    }
                } else {
                    log.error("Can't book for a past show: show time limit (+1h) {}, now {}", limit, now);
                    return null;
                }
            } else {
                log.error("Start time string with wrong format {}", dto.getStartTime());
                return null;
            }
        } else {
            log.error("Show id {} to book not found", dto.getShowId());
            return null;
        }
    }

    @Override
    public List<BookingDto> getBookings(String username) {
        List<Booking> bookings = bookingRepository.getBookings(username);
        return bookings.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public BookingDto getById(Long id) {
        Optional<Booking> bookingOpt = bookingRepository.findByIdWithFetch(id);
        return bookingOpt.map(this::toDto).orElse(null);
    }

    @Override
    public BookingCompactDto getByIdCompact(Long id) {
        Optional<Booking> bookingOpt = bookingRepository.findByIdWithFetch(id);
        return bookingOpt.map(this::toCompactDto).orElse(null);
    }

    @Override
    public Boolean deleteById(Long id) {
        Optional<Booking> bookingOpt = bookingRepository.findByIdWithFetch(id);
        if (bookingOpt.isPresent()) {
            Booking booking = bookingOpt.get();
            Show show = booking.getShow();
            String[] splittedTime = show.getStartTime().split(":");
            LocalDate showDate = DateUtils.convertToLocalDate(show.getDate());
            LocalTime showTime = LocalTime.of(Integer.parseInt(splittedTime[0]), Integer.parseInt(splittedTime[1]));
            LocalDateTime showDateTime = LocalDateTime.of(showDate, showTime);
            LocalDateTime now = LocalDateTime.now();

            if (!now.isAfter(showDateTime) && booking.getStatus() != BookingStatus.CHECKEDIN) {
                booking.setStatus(BookingStatus.CANCELED);
                bookingRepository.save(booking);
                return true;
            } else {
                log.error("Booking id {} with status {}, time {} can't be deleted", id, booking.getStatus(), showDateTime);
            }
        } else {
            log.error("Booking id {} to delete not found", id);
        }
        return false;
    }

    @Override
    public Boolean validateById(Long id, String username) {
        Optional<Booking> bookingOpt = bookingRepository.findByIdWithFetch(id);
        Optional<User> cashierOpt = userRepository.findFirstByUsername(username);
        if (bookingOpt.isPresent() && cashierOpt.isPresent()) {
            Booking booking = bookingOpt.get();
            User cashier = cashierOpt.get();

            if (cashier.getTheaterCashier() != null && Objects.equals(cashier.getTheaterCashier().getId(), booking.getShow().getScreen().getTheater().getId())) {
                // TODO: gestione created vs paid
                if (booking.getStatus() == BookingStatus.CREATED || booking.getStatus() == BookingStatus.PAID) {

                    List<SmartBandCompactDto> smartbands = contextBrokerService.searchSmartBands();
                    List<SmartBand> theaterSmartbands = smartBandRepository.findByTheaterId(
                            booking.getShow().getScreen().getTheater().getId()
                    );

                    List<SmartBand> availableSmartbands = theaterSmartbands.stream().filter(s -> {
                        Optional<SmartBandCompactDto> found = smartbands.stream()
                                .filter(sb -> Objects.equals(sb.getId(), s.getContextBrokerId())).findFirst();
                        return found.isPresent() && (found.get().getOnInfo() == null || !Objects.equals(found.get().getOnInfo().getValue(), "true"));
                    }).toList();

                    if (availableSmartbands.size() >= booking.getSeats().size()) {
                        List<SmartBand> toActivate = availableSmartbands.stream().limit(booking.getSeats().size()).toList();
                        toActivate.stream()
                                .takeWhile(s -> contextBrokerService.sendCommand(s.getContextBrokerId(), "on", "true"))
                                .forEach(s -> {
                                    deviceActivityService.create(s, booking);
                        });

                        booking.setStatus(BookingStatus.CHECKEDIN);
                        bookingRepository.save(booking);

                        return true;
                    } else {
                        log.error("Not enough smartbands available: found {}, needed {}",
                                availableSmartbands.size(), booking.getSeats().size());
                    }
                } else {
                    log.error("Booking id {} with status {} can't be validated", id, booking.getStatus());
                }
            } else {
                log.error("Booking id {} can't be validated by user {}", id, username);
            }
        } else {
            if (cashierOpt.isPresent()) {
                log.error("Booking id {} to validate not found", id);
            } else {
                log.error("User {} not found", username);
            }
        }
        return false;
    }

    private BookingDto toDto(Booking booking) {
        Show show = booking.getShow();
        Movie movie = show.getMovie();
        Theater theater = show.getScreen().getTheater();
        User user = booking.getUser();

        BookingDto dto = new BookingDto();
        dto.setId(booking.getId());
        dto.setPrice(booking.getPrice());
        dto.setStatus(booking.getStatus());
        dto.setShowId(show.getId());
        dto.setDate(show.getDate());
        dto.setStartTime(show.getStartTime());

        dto.setTheater(TheaterDto.toDto(theater));
        dto.setCity(theater.getAddress().getCity());

        dto.setMovie(movieService.toDto(movie));

        List<SeatDto> seats = booking.getSeats().stream().map(s -> new SeatDto(s.getId(), s.getLabel()))
                .collect(Collectors.toList());
        dto.setSeats(seats);

        dto.setCodeUrl(baseUrl + "/api/bookings/code/" + booking.getId());

        dto.setUsername(user.getUsername());

        return dto;
    }

    private BookingCompactDto toCompactDto(Booking booking) {
        Show show = booking.getShow();
        Movie movie = show.getMovie();
        Theater theater = show.getScreen().getTheater();

        BookingCompactDto dto = new BookingCompactDto();
        dto.setId(booking.getId());
        dto.setPrice(booking.getPrice());
        dto.setStatus(booking.getStatus());

        dto.setDate(show.getDate());
        dto.setStartTime(show.getStartTime());

        dto.setTheaterName(theater.getName());
        dto.setCity(theater.getAddress().getCity());

        dto.setMovieName(movie.getName());

        dto.setTotalSeats(booking.getSeats().size());

        return dto;
    }
}
