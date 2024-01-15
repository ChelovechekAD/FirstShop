package com.example.firstshop.controllers;

import com.example.firstshop.database.User;
import com.example.firstshop.dto.request.JwtRequest;
import com.example.firstshop.dto.request.UserLoginRequest;
import com.example.firstshop.dto.request.UserRegistrationRequest;
import com.example.firstshop.dto.response.JwtResponse;
import com.example.firstshop.dto.response.LoginResponse;
import com.example.firstshop.dto.response.RegistrationResponse;
import com.example.firstshop.repository.UserRepository;
import com.example.firstshop.services.AuthService;
import com.example.firstshop.services.UserService;
import com.example.firstshop.utils.JwtAuthentication;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final AuthService authService;

    @GetMapping (value = "/login")
    public String login(Model model){
        model.addAttribute("userForm", new UserLoginRequest());
        model.addAttribute("title", "Авторизация");
        return "auth_reg";
    }

    @PostMapping(value = "/login")
    public String login(@ModelAttribute("userForm")UserLoginRequest user, Model model){
        System.out.println(user.getEmail() + user.getPassword());
        model.addAttribute("title", "Авторизация");
        try{
            LoginResponse resp = userService.userLogin(user);
            model.addAttribute("email", resp.getEmail());
            model.addAttribute("login", resp.getUsername());
            //model.addAttribute("token", resp.getToken());
        }catch (Exception e){
            model.addAttribute("error", e.getMessage());
            return "auth_reg";
        }
        return "auth_reg";
    }
    @PostMapping (value = "/login_postman")
    public ResponseEntity<JwtResponse> login_postman(@RequestBody JwtRequest request){
        User user = userRepository.findByEmail(request.getEmail());
        return ResponseEntity.ok(authService.login(request, user));
    }

    @GetMapping(value = "/userInfo")
    public JwtAuthentication userInfo(){
        return authService.getUserInfo();
    }


    @GetMapping("/login_test")
    public ResponseEntity<JwtResponse> login() {
        JwtRequest authRequest = new JwtRequest("admin@admin.ru", "admin");
        User user  = userRepository.findByUsername("Admin");
        final JwtResponse token = authService.login(authRequest, user);
        return ResponseEntity.ok(token);
    }
}
