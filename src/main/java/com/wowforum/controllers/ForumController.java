package com.wowforum.controllers;

import com.wowforum.entities.Forum;
import com.wowforum.services.ForumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/forums")
public class ForumController {
    private final String ENDPOINT_NAME = "/api/v1/forums/";

    @Autowired
    private ForumService forumService;

    @GetMapping
    public ResponseEntity<List<Forum>> getAllForums() {
        var forums = forumService.getAllForums();
        return ResponseEntity.ok(forums);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Forum> createForum(@RequestBody Forum forum) {
        var createdForum = forumService.createForum(forum);
        var uri = URI.create(ENDPOINT_NAME + createdForum.getId());
        return ResponseEntity.created(uri).body(createdForum);
    }
}
