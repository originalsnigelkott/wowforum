package com.wowforum.controllers;

import com.wowforum.dtos.UserCreateDto;
import com.wowforum.dtos.UserReadDto;
import com.wowforum.dtos.UserUpdateDto;
import com.wowforum.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
  private static final String ENDPOINT_NAME = "/api/v1/users/";

  @Autowired
  private UserService userService;

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<UserReadDto> createUser(@Valid @RequestBody UserCreateDto user) {
    var createdUser = userService.createUser(user);
    var uri = URI.create(ENDPOINT_NAME + createdUser.getId());
    var dto = new UserReadDto(createdUser);
    return ResponseEntity.created(uri).body(dto);
  }

  @GetMapping
  @Secured("ROLE_ADMIN")
  public ResponseEntity<List<UserReadDto>> getUsers(@RequestParam(required = false) String username) {
    var users = userService.getUsers(username);
    var dtos = users.stream()
      .map(user -> new UserReadDto(user))
      .collect(Collectors.toList());
    return ResponseEntity.ok(dtos);
  }

  @PutMapping("{id}")
  @Secured("ROLE_ADMIN")
  public  ResponseEntity updateUser(@PathVariable UUID id, @Valid @RequestBody UserUpdateDto dto) {
    userService.updateUser(id, dto);
    return ResponseEntity.noContent().build();
  }
}
