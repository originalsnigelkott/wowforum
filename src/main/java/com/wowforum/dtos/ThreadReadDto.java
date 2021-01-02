package com.wowforum.dtos;

import com.wowforum.entities.Thread;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThreadReadDto extends BaseThreadDto {
    private UUID id;
    private List<PostReadDto> posts;

    public ThreadReadDto(Thread thread, Long postLimit) {
        super(thread.getTopic(), new PostReadDto());
        this.id = thread.getId();
        if (postLimit == 1L) {
            var postsSize = thread.getPosts().size();
            var lastPost = thread.getPosts().get(postsSize - 1);
            this.posts = List.of(new PostReadDto(lastPost));
        } else {
            this.posts = thread.getPosts().stream()
                    .map(post -> new PostReadDto())
                    .limit(postLimit)
                    .collect(Collectors.toList());
        }
    }
}


