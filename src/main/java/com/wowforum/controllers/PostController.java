package com.wowforum.controllers;

import com.wowforum.entities.Post;
import com.wowforum.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class PostController {
    @Autowired
    private PostService postService;

    @GetMapping("threads/{threadId}/posts")
    public ResponseEntity<List<Post>> getPostsByThreadId(@PathVariable UUID threadId) {
        var posts =  postService.getPostsByThreadId(threadId);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable UUID id) {
        var post =  postService.getPostById(id);
        return ResponseEntity.ok(post);
    }
}
