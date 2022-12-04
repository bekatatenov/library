package com.library.library.service;

import com.library.library.dao.UserRepository;
import com.library.library.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;

public class UsersService {

    @Autowired
    private UserRepository userRepository;

    public Users FindByLogin(String name) {

        return null;
    }
}
