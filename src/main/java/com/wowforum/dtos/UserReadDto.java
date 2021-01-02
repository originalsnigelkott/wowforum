package com.wowforum.dtos;

import com.wowforum.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class UserReadDto extends BaseUserDto {
    private UUID id;

    public UserReadDto(User user) {
        super(user.getUsername(), user.getFirstName(), user.getLastName(), user.getRoles());
        this.id = user.getId();
    }
}
