package com.wowforum.dtos;

import com.wowforum.entities.Post;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class PostReadDto extends BasePostDto {
    private UUID id;
    public PostReadDto(Post post) {
        super(post.getContent(), post.getCreated(), new UserReadDto(post.getCreator()));
        this.id = post.getId();
    }
}
