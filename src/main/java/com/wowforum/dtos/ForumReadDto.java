package com.wowforum.dtos;

import com.wowforum.entities.Forum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForumReadDto extends BaseForumDto {
    private List<ThreadReadDto> threads;

    public ForumReadDto(Forum forum, Long threadLimit) {
        super(forum.getId(), forum.getName(), forum.getDescription());
        this.threads = forum.getThreads().stream()
                .map(thread -> new ThreadReadDto(thread, 1L))
                .limit(threadLimit)
                .collect(Collectors.toList());
    }

    public ForumReadDto(Forum forum) {
        super(forum.getId(), forum.getName(), forum.getDescription());
        this.threads = forum.getThreads().stream()
                .map(thread -> new ThreadReadDto(thread, 1L))
                .collect(Collectors.toList());
    }

}