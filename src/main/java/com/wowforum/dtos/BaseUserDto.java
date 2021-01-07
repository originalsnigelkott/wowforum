package com.wowforum.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseUserDto {
  @NotBlank(message = "Username is mandatory.")
  @Size(min = 3, max = 40, message = "Username must be between {min} and {max} characters.")
  private String username;
  @NotBlank(message = "First name is mandatory.")
  @Size(min = 3, max = 40, message = "First name must be between {min} and {max} characters.")
  private String firstName;
  @NotBlank(message = "First name is mandatory.")
  @Size(min = 3, max = 40, message = "First name must be between {min} and {max} characters.")
  private String lastName;
  private String roles;
}
