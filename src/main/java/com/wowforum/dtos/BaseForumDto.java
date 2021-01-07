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
  @NotBlank(message = "Name can not be blank.")
  @Size(min = 3, max = 40, message = "Name must be between 3 and 40 characters.")
  private String name;
  @NotBlank(message = "Description can not be blank.")
  @Size(min = 3, message = "Description must at least 3 characters.")
  private String description;
}
