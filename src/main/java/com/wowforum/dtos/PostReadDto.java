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

    public PostReadDto(Post post) {
        super(post.getContent());
        this.id = post.getId();
        this.created = post.getCreated();
        this.creator = new UserReadDto(post.getCreator());
    }
}
