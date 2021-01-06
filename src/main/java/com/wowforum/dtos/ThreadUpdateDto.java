package com.wowforum.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThreadUpdateDto {
  private String topic;
  @JsonProperty
  private boolean isLocked;
}
