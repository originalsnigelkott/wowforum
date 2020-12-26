package com.wowforum.controllers;

import com.wowforum.entities.Thread;
import com.wowforum.services.ThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class ThreadController {
    private final String ENDPOINT_NAME = "/api/v1/threads/";

    @Autowired
    private ThreadService threadService;

    @PostMapping("/forums/{forumId}/threads")
    public ResponseEntity<Thread> createThread(@PathVariable UUID forumId, @RequestBody Thread thread) {
        var createdThread = threadService.createThread(forumId, thread);
        var uri = URI.create(ENDPOINT_NAME + createdThread.getId());
        return ResponseEntity.created(uri).body(createdThread);
    }
}
