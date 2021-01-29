package com.wowforum.controllers;

import com.wowforum.configs.MyUserDetailsService;
import com.wowforum.dtos.UserReadDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

  @Operation(summary = "Gets current user.", description = "Roles allowed: *")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Current user, null if unauthenticated.",
      content = {@Content(mediaType = "application/json",
        schema = @Schema(implementation = UserReadDto.class))}),
    @ApiResponse(responseCode = "500", description = "Server error.",
      content = @Content),
  })
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
