package com.example.cinemakiosk.service.impl;

import com.example.cinemakiosk.dto.contextbroker.PropertyDto;
import com.example.cinemakiosk.dto.contextbroker.SmartBandCompactDto;
import com.example.cinemakiosk.service.ContextBrokerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ContextBrokerServiceImpl implements ContextBrokerService {

    @Value("${context-broker.url}")
    private String baseUrl;

    @Value("${context-broker.service}")
    private String service;

    @Value("${context-broker.context-url}")
    private String context;

    @Override
    public List<SmartBandCompactDto> searchSmartBands() {
        RestTemplate restTemplate = new RestTemplate();

        String url = baseUrl + "/ngsi-ld/v1/entities?type=SmartBand&options=keyValues";

        HttpHeaders headers = getCommonHeaders();
        HttpEntity<Void> request = new HttpEntity<>(headers);

        try {
            ResponseEntity<List<SmartBandCompactDto>> response = restTemplate
                    .exchange(url, HttpMethod.GET, request, new ParameterizedTypeReference<>() {
            });
            return response.getBody();
        } catch (HttpStatusCodeException ex) {
            log.error("Context Broker error: {}", ex.getStatusCode());
            log.error(ex.getResponseBodyAsString());
            return new ArrayList<>();
        }
    }

    @Override
    public <T> Boolean sendCommand(String deviceId, String attr, T value) {
        RestTemplate restTemplate = new RestTemplate();

        String url = baseUrl + "/ngsi-ld/v1/entities/{deviceId}/attrs/{attr}";

        HttpHeaders headers = getCommonHeaders();

        PropertyDto<T> dto = new PropertyDto<>();
        dto.setType("Property");
        dto.setValue(value);

        HttpEntity<PropertyDto<T>> request = new HttpEntity<>(dto, headers);

        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

        try {
            restTemplate.exchange(url, HttpMethod.PATCH, request, Void.class, deviceId, attr);
            return true;
        } catch (HttpStatusCodeException ex) {
            log.error("Context Broker error: {}", ex.getStatusCode());
            log.error(ex.getResponseBodyAsString());
            return false;
        }
    }

    private HttpHeaders getCommonHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("NGSILD-Tenant", service);
        headers.add("Link", "<" + context + ">; rel=\"http://www.w3.org/ns/json-ld#context\"; type=\"application/ld+json\"");
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
