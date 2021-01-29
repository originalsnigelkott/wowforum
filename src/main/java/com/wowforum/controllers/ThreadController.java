package com.wowforum.controllers;

import com.wowforum.dtos.ThreadCreateDto;
import com.wowforum.dtos.ThreadReadDto;
import com.wowforum.dtos.ThreadUpdateDto;
import com.wowforum.services.ThreadService;
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
public class ThreadController {
  private final String ENDPOINT_NAME = "/api/v1/threads/";

  @Autowired
  private ThreadService threadService;

  @Operation(summary = "Get all threads by forum id", description = "Roles allowed: *")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "All threads",
      content = {@Content(mediaType = "application/json",
        array = @ArraySchema(schema = @Schema(implementation = ThreadReadDto.class)))}),
    @ApiResponse(responseCode = "400", description = "Invalid request parameters.",
      content = @Content),
    @ApiResponse(responseCode = "404", description = "Forum not found.",
      content = @Content),
    @ApiResponse(responseCode = "500", description = "Server error.",
      content = @Content),
  })
  @GetMapping("forums/{forumId}/threads")
  public ResponseEntity<List<ThreadReadDto>> getThreadsByForumId(@PathVariable UUID forumId) {
    var threads = threadService.getThreadsByForumId(forumId);
    var dtos = threads.stream()
      .map(thread -> new ThreadReadDto(thread, 3L))
      .collect(Collectors.toList());
    return ResponseEntity.ok(dtos);
  }

  @Operation(summary = "Get thread by id", description = "Roles allowed: *")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Thread found.",
      content = {@Content(mediaType = "application/json",
        schema = @Schema(implementation = ThreadReadDto.class))}),
    @ApiResponse(responseCode = "400", description = "Invalid request parameters.",
      content = @Content),
    @ApiResponse(responseCode = "404", description = "Thread not found.",
      content = @Content),
    @ApiResponse(responseCode = "500", description = "Server error.",
      content = @Content),
  })
  @GetMapping("threads/{id}")
  public ResponseEntity<ThreadReadDto> getThreadById(@PathVariable UUID id) {
    var thread = threadService.getThreadById(id);
    var dto = new ThreadReadDto(thread);
    return ResponseEntity.ok(dto);
  }

  @Operation(summary = "Creates thread", description = "Roles allowed: ADMIN, MODERATOR, USER")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Thread created.",
      content = {@Content(mediaType = "application/json",
        schema = @Schema(implementation = ThreadReadDto.class))}),
    @ApiResponse(responseCode = "400", description = "Invalid request parameters and/or body.",
      content = @Content),
    @ApiResponse(responseCode = "401", description = "Lacks authentication.",
      content = @Content),
    @ApiResponse(responseCode = "404", description = "Forum not found.",
      content = @Content),
    @ApiResponse(responseCode = "500", description = "Server error.",
      content = @Content),
  })
  @PostMapping("forums/{forumId}/threads")
  public ResponseEntity<ThreadReadDto> createThread(@PathVariable UUID forumId, @Valid @RequestBody ThreadCreateDto thread) {
    var createdThread = threadService.createThread(forumId, thread);
    var dto = new ThreadReadDto(createdThread);
    var uri = URI.create(ENDPOINT_NAME + createdThread.getId());
    return ResponseEntity.created(uri).body(dto);
  }

  @Operation(summary = "Delete thread by id", description = "Roles allowed: ADMIN, MODERATOR\nModerators can only delete posts forums they moderate.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "204", description = "Thread deleted.",
      content = @Content),
    @ApiResponse(responseCode = "400", description = "Invalid request parameters.",
      content = @Content),
    @ApiResponse(responseCode = "401", description = "Lacks authentication.",
      content = @Content),
    @ApiResponse(responseCode = "403", description = "Lacks permission.",
      content = @Content),
    @ApiResponse(responseCode = "404", description = "Thread not found.",
      content = @Content),
    @ApiResponse(responseCode = "500", description = "Server error.",
      content = @Content),
  })
  @DeleteMapping("threads/{id}")
  @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
  public ResponseEntity deleteThread(@PathVariable UUID id) {
    threadService.deleteThread(id);
    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "Update thread by id", description = "Roles allowed: ADMIN, MODERATOR\nModerators can only delete posts forums they moderate.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "204", description = "Thread updated.",
      content = @Content),
    @ApiResponse(responseCode = "400", description = "Invalid request parameters and/or body.",
      content = @Content),
    @ApiResponse(responseCode = "401", description = "Lacks authentication.",
      content = @Content),
    @ApiResponse(responseCode = "403", description = "Lacks permission.",
      content = @Content),
    @ApiResponse(responseCode = "404", description = "Thread not found.",
      content = @Content),
    @ApiResponse(responseCode = "500", description = "Server error.",
      content = @Content),
  })
  @PutMapping("threads/{id}")
  @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
  public ResponseEntity updateThread(@PathVariable UUID id, @Valid @RequestBody ThreadUpdateDto updateDto) {
    threadService.updateThread(id, updateDto);
    return ResponseEntity.noContent().build();
  }
}
