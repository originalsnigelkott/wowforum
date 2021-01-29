package com.wowforum.controllers;

import com.wowforum.dtos.ForumReadDto;
import com.wowforum.dtos.PostCreateDto;
import com.wowforum.dtos.PostReadDto;
import com.wowforum.services.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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

  @Operation(summary = "Get all posts by thread id", description = "Roles allowed: *")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "All posts",
      content = {@Content(mediaType = "application/json",
        array = @ArraySchema(schema = @Schema(implementation = PostReadDto.class)))}),
    @ApiResponse(responseCode = "400", description = "Invalid request parameters.",
      content = @Content),
    @ApiResponse(responseCode = "404", description = "Thread not found.",
      content = @Content),
    @ApiResponse(responseCode = "500", description = "Server error.",
      content = @Content),
  })
  @GetMapping("threads/{threadId}/posts")
  public ResponseEntity<List<PostReadDto>> getPostsByThreadId(@PathVariable UUID threadId) {
    var posts = postService.getPostsByThreadId(threadId);
    var dtos = posts.stream()
      .map(post -> new PostReadDto(post))
      .collect(Collectors.toList());
    return ResponseEntity.ok(dtos);
  }

  @Operation(summary = "Get posts by id", description = "Roles allowed: *")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Post found.",
      content = {@Content(mediaType = "application/json",
        schema = @Schema(implementation = PostReadDto.class))}),
    @ApiResponse(responseCode = "400", description = "Invalid request parameters.",
      content = @Content),
    @ApiResponse(responseCode = "404", description = "Post not found.",
      content = @Content),
    @ApiResponse(responseCode = "500", description = "Server error.",
      content = @Content),
  })
  @GetMapping("posts/{id}")
  public ResponseEntity<PostReadDto> getPostById(@PathVariable UUID id) {
    var post = postService.getPostById(id);
    var dto = new PostReadDto(post);
    return ResponseEntity.ok(dto);
  }

  @Operation(summary = "Creates post", description = "Roles allowed: ADMIN, MODERATOR, USER")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Post created.",
      content = {@Content(mediaType = "application/json",
        schema = @Schema(implementation = PostReadDto.class))}),
    @ApiResponse(responseCode = "400", description = "Invalid request parameters and/or body.",
      content = @Content),
    @ApiResponse(responseCode = "401", description = "Lacks authentication.",
      content = @Content),
    @ApiResponse(responseCode = "404", description = "Thread not found.",
      content = @Content),
    @ApiResponse(responseCode = "500", description = "Server error.",
      content = @Content),
  })
  @PostMapping("threads/{threadId}/posts")
  public ResponseEntity<PostReadDto> createPost(@PathVariable UUID threadId, @Valid @RequestBody PostCreateDto post) {
    var createdPost = postService.createPost(threadId, post);
    var dto = new PostReadDto(createdPost);
    var uri = URI.create(ENDPOINT_NAME + createdPost.getId());
    return ResponseEntity.created(uri).body(dto);
  }

  @Operation(summary = "Delete post by id", description = "Roles allowed: ADMIN, MODERATOR\nModerators can only delete posts forums they moderate.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "204", description = "Post deleted.",
      content = @Content),
    @ApiResponse(responseCode = "400", description = "Invalid request parameters.",
      content = @Content),
    @ApiResponse(responseCode = "401", description = "Lacks authentication.",
      content = @Content),
    @ApiResponse(responseCode = "403", description = "Lacks permission.",
      content = @Content),
    @ApiResponse(responseCode = "404", description = "Post not found.",
      content = @Content),
    @ApiResponse(responseCode = "500", description = "Server error.",
      content = @Content),
  })
  @DeleteMapping("posts/{id}")
  @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
  public ResponseEntity deletePostById(@PathVariable UUID id) {
    postService.deletePostById(id);
    return ResponseEntity.noContent().build();
  }
}
