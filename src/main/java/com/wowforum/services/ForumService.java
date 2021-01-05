package com.wowforum.services;

import com.wowforum.entities.Forum;
import com.wowforum.exceptions.EntityNotFoundException;
import com.wowforum.repositories.ForumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ForumService {
  @Autowired
  private ForumRepository forumRepository;

  public List<Forum> getAllForums() {
    return forumRepository.findAll();
  }

  public Forum getForumById(UUID id) {
    return forumRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("forum", "id"));
  }

  public Forum createForum(Forum forum) {
    return forumRepository.save(forum);
  }
}
