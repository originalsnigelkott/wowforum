package com.wowforum.dtos;

import com.wowforum.entities.Post;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class PostReadDto extends BasePostDto {
  private UUID id;
  private Long created;
  private UserReadDto creator;
  private UUID threadId;

  public PostReadDto(Post post) {
    super(post.getContent(), post.isWarning());
    this.id = post.getId();
    this.created = post.getCreated();
    if (post.getCreator() != null) {
      this.creator = new UserReadDto(post.getCreator());
    }
    this.threadId = post.getThread().getId();
  }
}
