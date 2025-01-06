package com.system.journal.usermanagement.resource;

import com.system.journal.usermanagement.model.*;
import com.system.journal.usermanagement.service.UserService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.CloseableThreadContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<SignUpUserResponseDTO> signUp(@RequestBody @Valid SignupUserRequestDTO requestDTO){
        try(CloseableThreadContext.Instance ctx  = CloseableThreadContext.put("transId","test")) {
            SignUpUserResponseDTO responseDTO = userService.registerUser(requestDTO);
            return  new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> logIn(@RequestBody @Valid LoginRequestDTO loginRequestDTO){
          LoginResponseDTO loginResponseDTO = userService.logInUser(loginRequestDTO);
          return new ResponseEntity<>(loginResponseDTO,HttpStatus.OK);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<LoginResponseDTO> refreshToken(@RequestBody @Valid RefreshTokenRequestDTO requestDTO){
        LoginResponseDTO responseDTO = userService.validateRefreshToken(requestDTO);
        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }


}
