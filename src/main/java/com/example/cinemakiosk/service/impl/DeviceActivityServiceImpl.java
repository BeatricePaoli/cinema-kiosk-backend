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

@Slf4j
@Service
public class DeviceActivityServiceImpl implements DeviceActivityService {

    @Autowired
    private DeviceActivityRepository deviceActivityRepository;

    @Autowired
    private ScreenRepository screenRepository;

    @Override
    public void create(SmartBand smartBand, Booking booking) {
        DeviceActivity activity = new DeviceActivity();
        activity.setActivationTms(new Date());
        activity.setBooking(booking);
        activity.setSmartBand(smartBand);

        deviceActivityRepository.save(activity);
    }

    // TODO: gestione ordini bar (altro metodo) e disattivazione (altro metodo)
    @Override
    public void update(NotificationDto<SmartBandDto> dto) {
        dto.getData().stream().forEach(s -> {
            List<DeviceActivity> activities = deviceActivityRepository.findActiveBySmartBandId(s.getId());
            if (activities.size() == 1) {
                DeviceActivity activity = activities.get(0);

                Theater theater = activity.getSmartBand().getTheater();
                Screen showScreen = activity.getBooking().getShow().getScreen();
                List<Screen> screens = screenRepository.findByTheaterId(theater.getId());
                Bar bar = theater.getBar();

                String newEmitterSerial = s.getEmitterSerial().getValue();
                boolean hasEnteredWrongRoom = screens.stream().anyMatch(sc -> Objects.equals(sc.getEmitterSerial(), newEmitterSerial) && !Objects.equals(sc.getId(), showScreen.getId()));

                boolean updated = false;
                if (Objects.equals(newEmitterSerial, bar.getEmitterSerial())) {
                    activity.setHasEnteredBar(true);
                    updated = true;
                } else if (hasEnteredWrongRoom) {
                    activity.setHasEnteredWrongRoom(true);
                    updated = true;
                } else if (Objects.equals(newEmitterSerial, "")) {
                    activity.setHasLeftTheater(true);
                    updated = true;
                }

                if (updated) {
                    activity.setLastUpdateTms(s.getEmitterSerial().getObservedAt());
                    deviceActivityRepository.save(activity);
                }
            } else {
                log.error("Update device activity error: no activity found or multiple active activities found");
            }
        });
    }
}
