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
  @NotBlank(message = "Description is mandatory.")
  @Size(min = 3, message = "Description must at least 3 characters.")
  private String content;
  @NotNull
  @JsonProperty
  private boolean isWarning;
}
