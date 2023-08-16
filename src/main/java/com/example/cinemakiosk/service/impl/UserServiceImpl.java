package com.example.cinemakiosk.service.impl;

import com.example.cinemakiosk.model.User;
import com.example.cinemakiosk.repository.UserRepository;
import com.example.cinemakiosk.service.KeycloakService;
import com.example.cinemakiosk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private KeycloakService keycloakService;

    @Override
    public User getOrCreateByUsername(String username) {
        Optional<User> userOpt = userRepository.findFirstByUsername(username);
        if (userOpt.isPresent()) {
            return userOpt.get();
        } else if (keycloakService.isUserValid(username)) {
            User user = new User();
            user.setUsername(username);
            return userRepository.save(user);
        }
        return null;
    }
}
