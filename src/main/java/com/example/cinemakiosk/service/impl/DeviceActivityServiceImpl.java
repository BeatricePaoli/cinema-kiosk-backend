package com.example.cinemakiosk.service.impl;

import com.example.cinemakiosk.dto.*;
import com.example.cinemakiosk.dto.contextbroker.NotificationDto;
import com.example.cinemakiosk.dto.contextbroker.SmartBandDto;
import com.example.cinemakiosk.model.*;
import com.example.cinemakiosk.repository.DeviceActivityRepository;
import com.example.cinemakiosk.repository.ScreenRepository;
import com.example.cinemakiosk.service.DeviceActivityService;
import com.example.cinemakiosk.service.MovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DeviceActivityServiceImpl implements DeviceActivityService {

    @Value("${base-url}")
    private String baseUrl;

    @Autowired
    private DeviceActivityRepository deviceActivityRepository;

    @Autowired
    private ScreenRepository screenRepository;

    @Autowired
    private MovieService movieService;

    @Override
    public void addActivationLog(SmartBand smartBand, Booking booking) {
        DeviceActivity activity = new DeviceActivity();
        activity.setTms(new Date());
        activity.setBooking(booking);
        activity.setSmartBand(smartBand);
        activity.setEventCode(DeviceActivityEvent.ACTIVATION);

        deviceActivityRepository.save(activity);
    }

    // TODO: gestione ordini bar (altro metodo) e disattivazione (altro metodo)
    @Override
    public void addEmitterLog(NotificationDto<SmartBandDto> dto) {
        dto.getData().stream().forEach(s -> {
            Optional<DeviceActivity> activityOpt = deviceActivityRepository.findLastBySmartBandId(s.getId());
            if (activityOpt.isPresent()) {
                DeviceActivity activity = activityOpt.get();

                if (activity.getEventCode() != DeviceActivityEvent.DEACTIVATION) {
                    DeviceActivity newActivity = new DeviceActivity();

                    newActivity.setTms(s.getEmitterSerial().getObservedAt());
                    newActivity.setEmitterSerial(s.getEmitterSerial().getValue());
                    newActivity.setBooking(activity.getBooking());
                    newActivity.setSmartBand(activity.getSmartBand());

                    Theater theater = activity.getSmartBand().getTheater();
                    Screen showScreen = activity.getBooking().getShow().getScreen();
                    List<Screen> screens = screenRepository.findByTheaterId(theater.getId());
                    Bar bar = theater.getBar();

                    String newEmitterSerial = s.getEmitterSerial().getValue();
                    boolean hasEnteredWrongRoom = screens.stream().anyMatch(sc -> Objects.equals(sc.getEmitterSerial(), newEmitterSerial) && !Objects.equals(sc.getId(), showScreen.getId()));

                    if (Objects.equals(newEmitterSerial, bar.getEmitterSerial())) {
                        newActivity.setEventCode(DeviceActivityEvent.ENTERED_BAR);
                    } else if (hasEnteredWrongRoom) {
                        newActivity.setEventCode(DeviceActivityEvent.WRONG_ROOM);
                    } else {
                        newActivity.setEventCode(DeviceActivityEvent.ROOM_CHANGE);
                    }
                    deviceActivityRepository.save(newActivity);
                } else {
                    log.error("Deactivation log found for device {}", s.getId());
                }
            } else {
                log.error("No activation log found for the device {}", s.getId());
            }
        });
    }

    @Override
    public Boolean addDeactivationLog(SmartBand smartBand) {
        Optional<DeviceActivity> activityOpt = deviceActivityRepository.findLastBySmartBandId(smartBand.getContextBrokerId());
        if (activityOpt.isPresent()) {
            DeviceActivity activity = activityOpt.get();
            if (activity.getEventCode() != DeviceActivityEvent.DEACTIVATION) {
                DeviceActivity newActivity = new DeviceActivity();

                newActivity.setTms(new Date());
                newActivity.setBooking(activity.getBooking());
                newActivity.setSmartBand(activity.getSmartBand());
                newActivity.setEventCode(DeviceActivityEvent.DEACTIVATION);

                deviceActivityRepository.save(newActivity);
                return true;
            } else {
                log.error("Deactivation log already found for device {}", smartBand.getId());
            }
        } else {
            log.error("No activation log found for the device {}", smartBand.getId());
        }
        return false;
    }

    @Override
    public List<DeviceActivityDto> getActivities(Long id) {
        List<DeviceActivity> activities = deviceActivityRepository.findBySmartbandId(id);
        return activities.stream().map(this::toDto).collect(Collectors.toList());
    }

    private DeviceActivityDto toDto(DeviceActivity activity) {
        DeviceActivityDto dto = new DeviceActivityDto();
        BeanUtils.copyProperties(activity, dto);
        dto.setProduct(activity.getProduct() != null ? BarProductDto.toDto(activity.getProduct()) : null);
        dto.setBooking(toDto(activity.getBooking()));
        return dto;
    }

    // TODO: copia del metodo in BookingServiceImpl
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
}
