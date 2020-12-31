package com.wowforum.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseUserDto {
    private UUID id;
    private String username;
    private String firstName;
    private String lastName;
    private String roles;
}
