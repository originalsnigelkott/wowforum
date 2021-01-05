package com.wowforum.services;

import com.wowforum.entities.Forum;
import com.wowforum.exceptions.EntityNotFoundException;
import com.wowforum.repositories.ForumRepository;
import com.wowforum.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ForumService {
  @Autowired
  private ForumRepository forumRepository;

  @Autowired
  private UserRepository userRepository;

  public List<Forum> getAllForums() {
    return forumRepository.findAll();
  }

  public Forum getForumById(UUID id) {
    return forumRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("forum", "id"));
  }

  public Forum createForum(Forum forum) {
    return forumRepository.save(forum);
  }

  public void addModerator(UUID forumId, UUID userId) {
    var forum = forumRepository.findById(forumId).orElseThrow(() -> new EntityNotFoundException("forum", "id"));
    var user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("user", "id"));
    if(!user.getRoles().contains("MODERATOR")) {
      user.setRoles(user.getRoles() + ",MODERATOR");
    }
    forum.getModerators().add(user);
    forumRepository.save(forum);
  }
}
