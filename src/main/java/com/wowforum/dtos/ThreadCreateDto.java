package com.wowforum.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThreadCreateDto {
  @NotBlank(message = "Topic is mandatory.")
  @Size(min = 3, max = 60, message = "Topic must be between {min} and {max} characters.")
  private String topic;
  @NotNull
  @Valid
  private PostCreateDto initialPost;
}
