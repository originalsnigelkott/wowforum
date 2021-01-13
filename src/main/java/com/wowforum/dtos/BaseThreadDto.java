package com.wowforum.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseThreadDto {
  private String topic;
  private PostReadDto initialPost;
  private boolean isLocked;
}
