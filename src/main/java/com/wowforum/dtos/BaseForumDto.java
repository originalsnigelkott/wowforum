package com.wowforum.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseForumDto {
  @NotBlank(message = "Name is mandatory.")
  @Size(min = 3, max = 60, message = "Name must be between {min} and {max} characters.")
  private String name;
  @NotBlank(message = "Description is mandatory.")
  @Size(min = 3, message = "Description must at least {min} characters.")
  private String description;
}
