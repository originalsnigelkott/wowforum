package com.wowforum.services;

import com.wowforum.entities.Post;
import com.wowforum.exceptions.EntityNotFoundException;
import com.wowforum.repositories.PostRepository;
import com.wowforum.repositories.ThreadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    public List<Post> getPostsByThreadId(UUID threadId) {
        return postRepository.findAllByThreadId(threadId);
    }

    public Post getPostById(UUID id) {
        return postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("post", "id"));
    }
}
