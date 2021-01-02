package com.wowforum.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasePostDto {
    private String content;
    private Long created;
    private UserReadDto creator;
}
