package com.wowforum.services;

import com.wowforum.configs.MyUserDetailsService;
import com.wowforum.dtos.ThreadCreateDto;
import com.wowforum.entities.Thread;
import com.wowforum.exceptions.EntityNotFoundException;
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
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Need to be logged in to complete this request.");
    }

    var thread = new Thread(threadDto, forumId, creator);

    return threadRepository.save(thread);
  }
}
