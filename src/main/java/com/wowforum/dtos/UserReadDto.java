package com.wowforum.dtos;

import com.wowforum.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserReadDto extends BaseUserDto {
    public UserReadDto(User user) {
        super(user.getId(), user.getUsername(), user.getFirstName(), user.getLastName(), user.getRoles());
    }
}
