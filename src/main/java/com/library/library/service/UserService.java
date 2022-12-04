package com.library.library.service;

import com.library.library.dao.UserRepository;
import com.library.library.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


import java.util.ArrayList;
import java.util.List;

public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public void save(Users user){
        this.userRepository.save(user);
    }

    public void findUserById(Long id){
        this.userRepository.findById(id).orElse(null);
    }

    public Users findByEmail (String email){
        return userRepository.findByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findFirstByEmail(username);
        if(user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().name()));
        return new User(user.getEmail(), user.getPassword(), authorities);
    }
}
