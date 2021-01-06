package com.wowforum.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThreadUpdateDto {
  private String topic;
  private boolean isLocked;
}
