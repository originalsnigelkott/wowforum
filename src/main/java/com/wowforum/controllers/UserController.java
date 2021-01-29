package com.wowforum.controllers;

import com.wowforum.dtos.UserCreateDto;
import com.wowforum.dtos.UserReadDto;
import com.wowforum.dtos.UserUpdateDto;
import com.wowforum.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

  @Operation(summary = "Creates user", description = "Roles allowed: *")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "User created.",
      content = {@Content(mediaType = "application/json",
        schema = @Schema(implementation = UserReadDto.class))}),
    @ApiResponse(responseCode = "400", description = "Invalid request body.",
      content = @Content),
    @ApiResponse(responseCode = "500", description = "Server error.",
      content = @Content),
  })
  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<UserReadDto> createUser(@Valid @RequestBody UserCreateDto user) {
    var createdUser = userService.createUser(user);
    var uri = URI.create(ENDPOINT_NAME + createdUser.getId());
    var dto = new UserReadDto(createdUser);
    return ResponseEntity.created(uri).body(dto);
  }

  @Operation(summary = "Get all users. Optional username query", description = "Roles allowed: ADMIN")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Users found.",
      content = {@Content(mediaType = "application/json",
        schema = @Schema(implementation = UserReadDto.class))}),
    @ApiResponse(responseCode = "400", description = "Invalid request parameters.",
      content = @Content),
    @ApiResponse(responseCode = "401", description = "Lacks authentication.",
      content = @Content),
    @ApiResponse(responseCode = "403", description = "Lacks permission.",
      content = @Content),
    @ApiResponse(responseCode = "500", description = "Server error.",
      content = @Content),
  })
  @GetMapping
  @Secured("ROLE_ADMIN")
  public ResponseEntity<List<UserReadDto>> getUsers(@RequestParam(required = false) String username) {
    var users = userService.getUsers(username);
    var dtos = users.stream()
      .map(user -> new UserReadDto(user))
      .collect(Collectors.toList());
    return ResponseEntity.ok(dtos);
  }

  @Operation(summary = "Updates user by id.", description = "Roles allowed: ADMIN")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "204", description = "User updated.",
      content = @Content),
    @ApiResponse(responseCode = "400", description = "Invalid request parameters.",
      content = @Content),
    @ApiResponse(responseCode = "401", description = "Lacks authentication.",
      content = @Content),
    @ApiResponse(responseCode = "403", description = "Lacks permission.",
      content = @Content),
    @ApiResponse(responseCode = "404", description = "User not found.",
      content = @Content),
    @ApiResponse(responseCode = "500", description = "Server error.",
      content = @Content),
  })
  @PutMapping("{id}")
  @Secured("ROLE_ADMIN")
  public ResponseEntity updateUser(@PathVariable UUID id, @Valid @RequestBody UserUpdateDto dto) {
    userService.updateUser(id, dto);
    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "Updates user by id.", description = "Roles allowed: ADMIN")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "204", description = "User deleted.",
      content = @Content),
    @ApiResponse(responseCode = "400", description = "Invalid request parameters.",
      content = @Content),
    @ApiResponse(responseCode = "401", description = "Lacks authentication.",
      content = @Content),
    @ApiResponse(responseCode = "403", description = "Lacks permission.",
      content = @Content),
    @ApiResponse(responseCode = "404", description = "User not found.",
      content = @Content),
    @ApiResponse(responseCode = "500", description = "Server error.",
      content = @Content),
  })
  @DeleteMapping("{id}")
  @Secured("ROLE_ADMIN")
  public ResponseEntity deleteUser(@PathVariable UUID id) {
    userService.deleteUser(id);
    return ResponseEntity.noContent().build();
  }
}
