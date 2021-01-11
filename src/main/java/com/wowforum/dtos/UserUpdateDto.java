package com.wowforum.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDto {
  @NotNull(message = "Roles is mandatory")
  private String roles;
}
