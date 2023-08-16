package com.example.cinemakiosk.service.impl;

import com.example.cinemakiosk.service.KeycloakService;
import jakarta.annotation.PostConstruct;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KeycloakServiceImpl implements KeycloakService {

    @Value("${auth.client-id.confidential}")
    private String clientId;

    @Value("${auth.client-secret.confidential}")
    private String clientSecret;

    @Value("${auth.realm}")
    private String realm;

    @Value("${auth.server-url}")
    private String serverUrl;

    Keycloak keycloak;

    @PostConstruct
    public void init() {
        keycloak = KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm(realm)
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .build();
    }

    @Override
    public boolean isUserValid(String username) {
        List<UserRepresentation> users = keycloak.realm(realm).users().searchByUsername(username, true);
        return !users.isEmpty();
    }
}
