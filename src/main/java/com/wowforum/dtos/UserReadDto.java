package com.wowforum.dtos;

import com.wowforum.entities.Forum;
import com.wowforum.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class UserReadDto extends BaseUserDto {
  private UUID id;
  private List<UUID> moderates;

  public UserReadDto(User user) {
    super(user.getUsername(), user.getFirstName(), user.getLastName(), user.getRoles());
    this.id = user.getId();
    this.moderates = user.getModeratedForums().stream()
      .map(Forum::getId)
      .collect(Collectors.toList());
  }
}
