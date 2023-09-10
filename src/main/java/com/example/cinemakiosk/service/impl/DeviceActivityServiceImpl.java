package com.example.cinemakiosk.service.impl;

import com.example.cinemakiosk.dto.contextbroker.NotificationDto;
import com.example.cinemakiosk.dto.contextbroker.SmartBandDto;
import com.example.cinemakiosk.model.*;
import com.example.cinemakiosk.repository.DeviceActivityRepository;
import com.example.cinemakiosk.repository.ScreenRepository;
import com.example.cinemakiosk.service.DeviceActivityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class DeviceActivityServiceImpl implements DeviceActivityService {

    @Autowired
    private DeviceActivityRepository deviceActivityRepository;

    @Autowired
    private ScreenRepository screenRepository;

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
                }
            } else {
                log.error("No activation log found for the device {}", s.getId());
            }
        });
    }
}
