package com.example.cinemakiosk.controller;

import com.example.cinemakiosk.dto.contextbroker.CashRegisterDto;
import com.example.cinemakiosk.dto.contextbroker.NotificationDto;
import com.example.cinemakiosk.dto.contextbroker.SmartbandDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/context-broker")
public class ContextBrokerController {

    @PostMapping(value = "smartband")
    public ResponseEntity<?> getNotificationSmartBand(@RequestBody NotificationDto<SmartbandDto> dto) {
        log.info("Data notifica: {}, seriale {}", dto.getNotifiedAt(), dto.getData().get(0).getEmitterSerial().getValue());
        return ResponseEntity.noContent().build();
    }

    // Scorpio non invia l'header Content-Type e quindi Spring Boot lo interpreta come octet-stream invece di json
    // e non riesce a deserializzare il body in automatico --> RISOLTO AGGIUNGENDO L'HEADER IN receiverInfo NELLA SUBSCRIBE
//    @PostMapping(value = "smartband", consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE)
//    public ResponseEntity<?> getNotificationSmartBand(HttpServletRequest request, @RequestBody String body) throws JsonProcessingException {
//        log.info("Header {}", request.getHeader("Content-Type"));
//        log.info(body);
//        ObjectMapper mapper = new ObjectMapper();
//        JavaType javaType = mapper.getTypeFactory().constructParametricType(NotificationDto.class, SmartbandDto.class);
//        NotificationDto<SmartbandDto> dto = mapper.readValue(body, javaType);
//        log.info("Data notifica: {}, seriale {}", dto.getNotifiedAt(), dto.getData().get(0).getEmitterSerial());
//        return ResponseEntity.noContent().build();
//    }

    @PostMapping(value = "cashRegister")
    public ResponseEntity<?> getNotificationCashRegister(@RequestBody NotificationDto<CashRegisterDto> dto) {
        log.info("Data notifica: {}", dto.getNotifiedAt());
        log.info("{}", dto.getData().get(0).getCashRegisterType().getValue());
        log.info("{}", dto.getData().get(0).getId());
        log.info("{}", dto.getData().get(0).getDevice().getObject());
        log.info("{}", dto.getData().get(0).getProduct().getObject());
        log.info("{}", dto.getData().get(0).getQuantity().getValue());
        return ResponseEntity.noContent().build();
    }
}
