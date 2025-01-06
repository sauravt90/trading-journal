package com.system.journal.usermanagement.service;

import com.system.journal.common.exception.CustomResponseException;
import com.system.journal.usermanagement.model.User;
import com.system.journal.usermanagement.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;

@Component
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {


    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOP =  userRepository.findByUserName(username);
        if(userOP.isEmpty()){
           String errMsg = String.format("User Not fount with userName : %s",username);
           log.warn(errMsg);
           throw new CustomResponseException(errMsg, HttpStatus.BAD_REQUEST);
        }

        User user = userOP.get();
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUserName())
                .password(user.getPassword())
                .build();

    }
}
