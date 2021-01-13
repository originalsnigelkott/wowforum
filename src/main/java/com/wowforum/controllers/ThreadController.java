package com.wowforum.controllers;

import com.wowforum.dtos.ThreadCreateDto;
import com.wowforum.dtos.ThreadReadDto;
import com.wowforum.dtos.ThreadUpdateDto;
import com.wowforum.services.ThreadService;
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
public class ThreadController {
  private final String ENDPOINT_NAME = "/api/v1/threads/";

  @Autowired
  private ThreadService threadService;

  @GetMapping("forums/{forumId}/threads")
  public ResponseEntity<List<ThreadReadDto>> getThreadsByForumId(@PathVariable UUID forumId) {
    var threads = threadService.getThreadsByForumId(forumId);
    var dtos =  threads.stream()
      .map(thread -> new ThreadReadDto(thread, 3L))
      .collect(Collectors.toList());
    return ResponseEntity.ok(dtos);
  }

  @GetMapping("threads/{id}")
  public ResponseEntity<ThreadReadDto> getThreadById(@PathVariable UUID id) {
    var thread = threadService.getThreadById(id);
    var dto = new ThreadReadDto(thread);
    return ResponseEntity.ok(dto);
  }

  @PostMapping("forums/{forumId}/threads")
  public ResponseEntity<ThreadReadDto> createThread(@PathVariable UUID forumId, @Valid @RequestBody ThreadCreateDto thread) {
    var createdThread = threadService.createThread(forumId, thread);
    var dto = new ThreadReadDto(createdThread);
    var uri = URI.create(ENDPOINT_NAME + createdThread.getId());
    return ResponseEntity.created(uri).body(dto);
  }

  @DeleteMapping("threads/{id}")
  @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
  public ResponseEntity deleteThread(@PathVariable UUID id) {
    threadService.deleteThread(id);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("threads/{id}")
  @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
  public ResponseEntity updateThread(@PathVariable UUID id, @Valid @RequestBody ThreadUpdateDto updateDto) {
    threadService.updateThread(id, updateDto);
    return ResponseEntity.noContent().build();
  }
}
