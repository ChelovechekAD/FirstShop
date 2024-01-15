package com.example.firstshop.controllers;

import com.example.firstshop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {

    UserService userService;

    @GetMapping (value = "/admin")
    public String userList(Model model){
        model.addAttribute("allUsers", userService.getAllUsers());
        return "admin";
    }

    @PostMapping (value = "/admin")
    public String actionUser(@RequestParam (required = true, defaultValue = "") Long userId,
                             @RequestParam (required = true, defaultValue = "") String action,
                             Model model){
        if (action.equals("delete")){
            userService.deleteUser(userId);
        }
        return "redirect:/admin";
    }

}
