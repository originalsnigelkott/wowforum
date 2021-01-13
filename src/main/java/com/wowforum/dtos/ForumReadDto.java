package com.wowforum.dtos;

import com.wowforum.entities.Forum;
import com.wowforum.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForumReadDto extends BaseForumDto {
  private UUID id;
  private List<ThreadReadDto> threads;
  private List<UUID> moderators;

  public ForumReadDto(Forum forum, Long threadLimit) {
    super(forum.getName(), forum.getDescription());
    this.id = forum.getId();
    this.threads = forum.getThreads().stream()
      .map(thread -> new ThreadReadDto(thread, 1L))
      .limit(threadLimit)
      .collect(Collectors.toList());
    this.moderators = getModeratorList(forum.getModerators());
  }

  public ForumReadDto(Forum forum) {
    super(forum.getName(), forum.getDescription());
    this.id = forum.getId();
    this.threads = forum.getThreads().stream()
      .map(thread -> new ThreadReadDto(thread, 1L))
      .collect(Collectors.toList());
    this.moderators = getModeratorList(forum.getModerators());
  }

  private List<UUID> getModeratorList(Set<User> moderators) {
    return moderators.stream()
      .map(user -> user.getId())
      .collect(Collectors.toList());
  }
}