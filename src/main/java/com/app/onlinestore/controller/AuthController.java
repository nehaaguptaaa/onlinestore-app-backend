package com.app.onlinestore.controller;

import com.app.onlinestore.dto.LoginDTO;
import com.app.onlinestore.dto.RegisterDTO;
import com.app.onlinestore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired private UserService userService;

    @PostMapping("/register")
    public String register(@RequestBody RegisterDTO dto) {
        return userService.register(dto);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginDTO dto) {
        return userService.login(dto);
    }
}