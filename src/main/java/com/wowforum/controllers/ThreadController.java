package com.wowforum.controllers;

import com.wowforum.dtos.ThreadCreateDto;
import com.wowforum.entities.Thread;
import com.wowforum.services.ThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class ThreadController {
  private final String ENDPOINT_NAME = "/api/v1/threads/";

  @Autowired
  private ThreadService threadService;

  @GetMapping("/forums/{forumId}/threads")
  public ResponseEntity<List<Thread>> getThreadsByForumId(@PathVariable UUID forumId) {
    var threads = threadService.getThreadsByForumId(forumId);
    return ResponseEntity.ok(threads);
  }

  @GetMapping("/threads/{id}")
  public ResponseEntity<Thread> getThreadById(@PathVariable UUID id) {
    var thread = threadService.getThreadById(id);
    return ResponseEntity.ok(thread);
  }

  @PostMapping("/forums/{forumId}/threads")
  public ResponseEntity<Thread> createThread(@PathVariable UUID forumId, @RequestBody ThreadCreateDto thread) {
    var createdThread = threadService.createThread(forumId, thread);
    var uri = URI.create(ENDPOINT_NAME + createdThread.getId());
    return ResponseEntity.created(uri).body(createdThread);
  }
}
