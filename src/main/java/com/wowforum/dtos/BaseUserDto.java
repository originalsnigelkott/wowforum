package com.wowforum.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseUserDto {
    private String username;
    private String firstName;
    private String lastName;
    private String roles;
}
