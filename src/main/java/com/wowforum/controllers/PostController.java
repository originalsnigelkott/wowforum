package com.wowforum.controllers;

import com.wowforum.dtos.PostCreateDto;
import com.wowforum.dtos.PostReadDto;
import com.wowforum.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class PostController {
  private final String ENDPOINT_NAME = "/api/v1/posts/";

  @Autowired
  private PostService postService;

  @GetMapping("threads/{threadId}/posts")
  public ResponseEntity<List<PostReadDto>> getPostsByThreadId(@PathVariable UUID threadId) {
    var posts = postService.getPostsByThreadId(threadId);
    var dtos = posts.stream()
      .map(post -> new PostReadDto(post))
      .collect(Collectors.toList());
    return ResponseEntity.ok(dtos);
  }

  @GetMapping("posts/{id}")
  public ResponseEntity<PostReadDto> getPostById(@PathVariable UUID id) {
    var post = postService.getPostById(id);
    var dto = new PostReadDto(post);
    return ResponseEntity.ok(dto);
  }

  @PostMapping("threads/{threadId}/posts")
  public ResponseEntity<PostReadDto> createPost(@PathVariable UUID threadId, @Valid @RequestBody PostCreateDto post) {
    var createdPost = postService.createPost(threadId, post);
    var dto = new PostReadDto(createdPost);
    var uri = URI.create(ENDPOINT_NAME + createdPost.getId());
    return ResponseEntity.created(uri).body(dto);
  }
}
