package com.wowforum.dtos;

import lombok.Data;

@Data
public class PostCreateDto extends BasePostDto {
    public PostCreateDto(String content) {
        super(content);
    }

    PostCreateDto() {
    }
}
