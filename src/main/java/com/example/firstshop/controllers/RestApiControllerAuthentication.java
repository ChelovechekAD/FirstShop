package com.example.firstshop.controllers;


import com.example.firstshop.database.User;
import com.example.firstshop.dto.request.*;
import com.example.firstshop.dto.response.DeleteUserResponse;
import com.example.firstshop.dto.response.JwtResponse;
import com.example.firstshop.dto.response.LoginResponse;
import com.example.firstshop.dto.response.RegistrationResponse;
import com.example.firstshop.services.JwtService;
import com.example.firstshop.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class RestApiControllerAuthentication {

    private final UserService userService;
    private final JwtService jwtService;

    @PostMapping(value = "/login")
    @ResponseBody
    public ResponseEntity<LoginResponse> login(@RequestBody JwtRequest request){
        LoginResponse response = userService.userLogin(new UserLoginRequest(request.getEmail(), request.getPassword()));
        jwtService.loginToken(new RefreshJwtRequest(response.getRefreshToken()));
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/registration")
    public ResponseEntity<RegistrationResponse> reg(@RequestBody UserRegistrationRequest request){
        return ResponseEntity.ok(userService.userRegistration(request));
    }

    @PostMapping(value="/delete")
    public ResponseEntity<DeleteUserResponse> delete(@RequestBody DeleteUserRequest request){
        return ResponseEntity.ok(userService.deleteUserByEmail(request));
    }

}
