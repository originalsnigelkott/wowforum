package com.wowforum.dtos;

import com.wowforum.entities.Forum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForumReadDto extends BaseForumDto {
  private UUID id;
  private List<ThreadReadDto> threads;

  public ForumReadDto(Forum forum, Long threadLimit) {
    super(forum.getName(), forum.getDescription());
    this.id = forum.getId();
    this.threads = forum.getThreads().stream()
      .map(thread -> new ThreadReadDto(thread, 1L))
      .limit(threadLimit)
      .collect(Collectors.toList());
  }

  public ForumReadDto(Forum forum) {
    super(forum.getName(), forum.getDescription());
    this.id = forum.getId();
    this.threads = forum.getThreads().stream()
      .map(thread -> new ThreadReadDto(thread, 1L))
      .collect(Collectors.toList());
  }

}