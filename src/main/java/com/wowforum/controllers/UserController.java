package com.wowforum.controllers;

import com.wowforum.dtos.UserCreateDto;
import com.wowforum.dtos.UserReadDto;
import com.wowforum.entities.Forum;
import com.wowforum.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private static final String ENDPOINT_NAME = "/api/v1/users";

    @Autowired
    private UserService userService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserReadDto> createForum(@RequestBody UserCreateDto user) {
        var createdUser = userService.createUser(user);
        var uri = URI.create(ENDPOINT_NAME + createdUser.getId());
        var dto = new UserReadDto(createdUser);
        return ResponseEntity.created(uri).body(dto);
    }

}
