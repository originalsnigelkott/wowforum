package com.wowforum.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDto extends BaseUserDto {
  @NotBlank(message = "Password is mandatory.")
  @Size(min = 6, message = "Password must at least {min} characters.")
  private String password;
}
