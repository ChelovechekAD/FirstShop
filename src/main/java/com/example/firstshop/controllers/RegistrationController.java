package com.example.firstshop.controllers;

import com.example.firstshop.database.User;
import com.example.firstshop.dto.request.UserRegistrationRequest;
import com.example.firstshop.dto.response.RegistrationResponse;
import com.example.firstshop.repository.UserRepository;
import com.example.firstshop.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class RegistrationController {
    UserService userService;

    @GetMapping (value = "/registration")
    public String registration(Model model){
        model.addAttribute("userForm", new UserRegistrationRequest());
        model.addAttribute("title", "Регистрация");
        return "auth_reg";
    }

    @PostMapping (value = "/registration")
    public String addUser(@ModelAttribute("userForm")UserRegistrationRequest user, Model model){
        model.addAttribute("title", "Регистрация");
        try{
            RegistrationResponse resp = userService.userRegistration(user);
            model.addAttribute("email", resp.getEmail());
        }catch (Exception e){
            model.addAttribute("error", e.getMessage());
            return "auth_reg";
        }
        return "redirect:/";
    }
}
