package com.system.journal.usermanagement.service;

import com.system.journal.common.exception.CustomResponseException;
import com.system.journal.usermanagement.model.*;
import com.system.journal.usermanagement.repository.UserRepository;
import com.system.journal.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, AuthenticationManager authenticationManager, JwtUtil jwtUtil,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    public SignUpUserResponseDTO registerUser(SignupUserRequestDTO signupUserRequestDTO){
        Optional<User> userOp = userRepository.findByUserName(signupUserRequestDTO.getUserName());
        if(userOp.isPresent()){
            String errMsg = String.format("Username already taken %s",signupUserRequestDTO.getUserName());
            throw new CustomResponseException(errMsg, HttpStatus.BAD_REQUEST);
        }

        Optional<User> userOp1 = userRepository.findByEmail(signupUserRequestDTO.getEmail());
        if(userOp1.isPresent()){
            String errMsg = String.format("User already exists with email %s",signupUserRequestDTO.getEmail());
            throw new CustomResponseException(errMsg, HttpStatus.BAD_REQUEST);
        }

        User user = User.builder()
                       .userName(signupUserRequestDTO.getUserName())
                        .email(signupUserRequestDTO.getEmail())
                        .firstName(signupUserRequestDTO.getFirstName())
                        .lastName(signupUserRequestDTO.getLastName())
                        .password(passwordEncoder.encode(signupUserRequestDTO.getPassword()))
                        .build();
        User user1 =  userRepository.save(user);
        return SignUpUserResponseDTO.builder()
                                    .Id(user1.getId())
                                    .userName(user1.getUserName())
                                    .lastName(user1.getLastName())
                                    .email(user1.getEmail())
                                    .build();

    }

    public LoginResponseDTO logInUser(LoginRequestDTO loginRequestDTO){
        Optional<User> userOp = userRepository.findByUserName(loginRequestDTO.getUserName());
        if(userOp.isEmpty()){
            String errMsg = String.format("UserName or password incorrect Please try again");
            throw new CustomResponseException(errMsg, HttpStatus.BAD_REQUEST);
        }
        User user = userOp.get();
        if(!passwordEncoder.matches(loginRequestDTO.getPassword(),user.getPassword())){
            String errMsg = String.format("UserName or password incorrect Please try again");
            throw new CustomResponseException(errMsg, HttpStatus.BAD_REQUEST);
        }

        String token = jwtUtil.generateToken(user.getUserName());
        String refreshToke = jwtUtil.generateRefreshToken(user.getUserName());
        return LoginResponseDTO.builder()
                .token(token)
                .refreshToken(refreshToke)
                .build();
    }

    public LoginResponseDTO validateRefreshToken(RefreshTokenRequestDTO requestDTO){
        String username = jwtUtil.extractUsername(requestDTO.getRefreshToken(),JwtUtil.REFRESH_SECRET_KEY);
        Optional<User> userOP = userRepository.findByUserName(username);
        if(userOP.isEmpty()){
            String errMsg = String.format("User not found with one in token");
            throw new CustomResponseException(errMsg, HttpStatus.BAD_REQUEST);
        }
        if(!jwtUtil.validateToken(requestDTO.getRefreshToken(),JwtUtil.REFRESH_SECRET_KEY)){
            throw new CustomResponseException("Refresh Token invalid",HttpStatus.BAD_REQUEST);
        }
        User user = userOP.get();
        String token = jwtUtil.generateToken(user.getUserName());
        String refreshToke = jwtUtil.generateRefreshToken(user.getUserName());
        return LoginResponseDTO.builder()
                .token(token)
                .refreshToken(refreshToke)
                .build();
    }
}
