package com.wowforum.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasePostDto {
  @NotBlank(message = "Content is mandatory.")
  @Size(min = 3, message = "Content must at least {min} characters.")
  private String content;
  @NotNull
  @JsonProperty
  private boolean isWarning;
}
