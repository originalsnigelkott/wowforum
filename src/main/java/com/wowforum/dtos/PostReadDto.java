package com.wowforum.dtos;

import com.wowforum.entities.Post;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostReadDto extends BasePostDto {
    public PostReadDto(Post post) {
        super(post.getId(), post.getContent(), post.getCreated(), new UserReadDto(post.getCreator()));
    }
}
