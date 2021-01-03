package com.wowforum.controllers;

import com.wowforum.configs.MyUserDetailsService;
import com.wowforum.dtos.UserReadDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @GetMapping("login")
    public ResponseEntity<UserReadDto> getCurrentUser() {
        var user = myUserDetailsService.getCurrentUser();
        if (user == null) {
            return ResponseEntity.ok(null);
        }
        var dto = new UserReadDto(user);
        return ResponseEntity.ok(dto);
    }
}
