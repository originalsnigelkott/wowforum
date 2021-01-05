package com.wowforum.controllers;

import com.wowforum.dtos.ForumReadDto;
import com.wowforum.entities.Forum;
import com.wowforum.services.ForumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/forums")
public class ForumController {
  private final String ENDPOINT_NAME = "/api/v1/forums/";

  @Autowired
  private ForumService forumService;

  @GetMapping
  public ResponseEntity<List<ForumReadDto>> getAllForums() {
    var forums = forumService.getAllForums();
    var dtos = forums.stream()
      .map(forum -> new ForumReadDto(forum, 3L))
      .collect(Collectors.toList());
    return ResponseEntity.ok(dtos);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ForumReadDto> getForumById(@PathVariable UUID id) {
    var forum = forumService.getForumById(id);
    var dto = new ForumReadDto(forum);
    return ResponseEntity.ok(dto);
  }

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @Secured("ROLE_ADMIN")
  public ResponseEntity<Forum> createForum(@RequestBody Forum forum) {
    var createdForum = forumService.createForum(forum);
    var uri = URI.create(ENDPOINT_NAME + createdForum.getId());
    return ResponseEntity.created(uri).body(createdForum);
  }

  @PostMapping("/{forumId}/moderators/{userId}")
  @Secured("ROLE_ADMIN")
  public ResponseEntity addModerator(@PathVariable UUID forumId, @PathVariable UUID userId ) {
    forumService.addModerator(forumId, userId);
    return ResponseEntity.ok("OK");
  }
}
