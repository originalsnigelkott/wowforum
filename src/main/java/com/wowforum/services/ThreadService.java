package com.wowforum.services;

import com.sun.xml.bind.v2.TODO;
import com.wowforum.configs.MyUserDetailsService;
import com.wowforum.dtos.ThreadCreateDto;
import com.wowforum.dtos.ThreadUpdateDto;
import com.wowforum.entities.Thread;
import com.wowforum.entities.User;
import com.wowforum.exceptions.EntityNotFoundException;
import com.wowforum.exceptions.ForbiddenException;
import com.wowforum.repositories.ForumRepository;
import com.wowforum.repositories.ThreadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class ThreadService {
  @Autowired
  private ThreadRepository threadRepository;

  @Autowired
  private ForumRepository forumRepository;

  @Autowired
  private MyUserDetailsService userDetailsService;

  public List<Thread> getThreadsByForumId(UUID forumId) {
    return threadRepository.findAllByForumId(forumId);
  }

  public Thread getThreadById(UUID id) {
    return threadRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("thread", "id"));
  }

  public Thread createThread(UUID forumId, ThreadCreateDto threadDto) {
    var creator = userDetailsService.getCurrentUser();
    if (creator == null) {
      throw new ForbiddenException("Need to be logged in to complete this request.");
    }
    var forum = forumRepository.findById(forumId).orElseThrow(() -> new EntityNotFoundException("forum", "id"));
    var thread = new Thread(threadDto, forum, creator);

    return threadRepository.save(thread);
  }
  public void deleteThread(UUID id) {
    var thread = getThreadById(id);
    checkPermissions(thread.getForum().getId());
    threadRepository.delete(thread);
  }

  public void updateThread(UUID id, ThreadUpdateDto updateDto) {
    var thread = getThreadById(id);
    checkPermissions(thread.getForum().getId());
    thread.setTopic(updateDto.getTopic());
    thread.setLocked(updateDto.isLocked());
    threadRepository.save(thread);
  }

  private void checkPermissions(UUID forumId) {
    var user = userDetailsService.getCurrentUser();
    if (user == null || !hasPermissionToForum(user, forumId)) {
      throw new ForbiddenException("User lacks permissions to this forum.");
    }
  }

  public boolean hasPermissionToForum(User user, UUID forumId) {
    return user.getRoles().contains("ADMIN") ||
      user.getModeratedForums().stream()
        .anyMatch(forum -> forumId.equals(forum.getId()));
  }
}
