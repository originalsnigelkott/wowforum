package com.wowforum.controllers;

import com.wowforum.dtos.BaseForumDto;
import com.wowforum.dtos.ForumReadDto;
import com.wowforum.services.ForumService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

  @Operation(summary = "Get all forums", description = "Roles allowed: *")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "All forums",
      content = {@Content(mediaType = "application/json",
        array = @ArraySchema(schema = @Schema(implementation = ForumReadDto.class)))}),
    @ApiResponse(responseCode = "500", description = "Server error.",
      content = @Content),
  })
  @GetMapping
  public ResponseEntity<List<ForumReadDto>> getAllForums() {
    var forums = forumService.getAllForums();
    var dtos = forums.stream()
      .map(forum -> new ForumReadDto(forum, 3L))
      .collect(Collectors.toList());
    return ResponseEntity.ok(dtos);
  }

  @Operation(summary = "Get forum by id.", description = "Roles allowed: *")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Forum found.",
      content = {@Content(mediaType = "application/json",
        schema = @Schema(implementation = ForumReadDto.class))}),
    @ApiResponse(responseCode = "404", description = "Forum not found.",
      content = @Content),
    @ApiResponse(responseCode = "500", description = "Server error.",
      content = @Content),
  })
  @GetMapping("{id}")
  public ResponseEntity<ForumReadDto> getForumById(@PathVariable UUID id) {
    var forum = forumService.getForumById(id);
    var dto = new ForumReadDto(forum);
    return ResponseEntity.ok(dto);
  }

  @Operation(summary = "Creates a new forum.", description = "Roles allowed: ADMIN")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Forum created",
      content = {@Content(mediaType = "application/json",
        schema = @Schema(implementation = ForumReadDto.class))}),
    @ApiResponse(responseCode = "400", description = "Invalid request body.",
      content = @Content),
    @ApiResponse(responseCode = "401", description = "Lacks authentication.",
      content = @Content),
    @ApiResponse(responseCode = "403", description = "Lacks permission.",
      content = @Content),
    @ApiResponse(responseCode = "500", description = "Server error.",
      content = @Content),
  })
  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @Secured("ROLE_ADMIN")
  public ResponseEntity<ForumReadDto> createForum(@Valid @RequestBody BaseForumDto forum) {
    var createdForum = forumService.createForum(forum);
    var dto = new ForumReadDto(createdForum);
    var uri = URI.create(ENDPOINT_NAME + createdForum.getId());
    return ResponseEntity.created(uri).body(dto);
  }

  @Operation(summary = "Deletes forum by id.", description = "Roles allowed: ADMIN")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "204", description = "Forum deleted",
      content = @Content),
    @ApiResponse(responseCode = "401", description = "Lacks authentication.",
      content = @Content),
    @ApiResponse(responseCode = "403", description = "Lacks permission.",
      content = @Content),
    @ApiResponse(responseCode = "404", description = "Forum not found.",
      content = @Content),
    @ApiResponse(responseCode = "500", description = "Server error.",
      content = @Content),
  })
  @DeleteMapping("{id}")
  @Secured("ROLE_ADMIN")
  public ResponseEntity deleteForum(@PathVariable UUID id) {
    forumService.deleteForum(id);
    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "Creates new moderator.", description = "Roles allowed: ADMIN")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "204", description = "Moderator created",
      content = @Content),
    @ApiResponse(responseCode = "400", description = "Invalid request parameters.",
      content = @Content),
    @ApiResponse(responseCode = "401", description = "Lacks authentication.",
      content = @Content),
    @ApiResponse(responseCode = "403", description = "Lacks permission.",
      content = @Content),
    @ApiResponse(responseCode = "404", description = "Forum and/or user not found.",
      content = @Content),
    @ApiResponse(responseCode = "500", description = "Server error.",
      content = @Content),
  })
  @PostMapping("{forumId}/moderators/{userId}")
  @Secured("ROLE_ADMIN")
  public ResponseEntity addModerator(@PathVariable UUID forumId, @PathVariable UUID userId) {
    forumService.addModerator(forumId, userId);
    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "Deletes moderator.", description = "Roles allowed: ADMIN")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "204", description = "Moderator deleted",
      content = @Content),
    @ApiResponse(responseCode = "400", description = "Invalid request parameters.",
      content = @Content),
    @ApiResponse(responseCode = "401", description = "Lacks authentication.",
      content = @Content),
    @ApiResponse(responseCode = "403", description = "Lacks permission.",
      content = @Content),
    @ApiResponse(responseCode = "404", description = "Forum and/or user not found.",
      content = @Content),
    @ApiResponse(responseCode = "500", description = "Server error.",
      content = @Content),
  })
  @DeleteMapping("{forumId}/moderators/{userId}")
  @Secured("ROLE_ADMIN")
  public ResponseEntity deleteModerator(@PathVariable UUID forumId, @PathVariable UUID userId) {
    forumService.deleteModerator(forumId, userId);
    return ResponseEntity.noContent().build();
  }
}
