package com.example.cinemakiosk.service;

import com.example.cinemakiosk.model.User;

public interface UserService {
    User getOrCreateByUsername(String username);
}
