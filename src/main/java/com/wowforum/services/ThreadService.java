package com.wowforum.services;

import com.wowforum.entities.Thread;
import com.wowforum.exceptions.EntityNotFoundException;
import com.wowforum.repositories.ThreadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ThreadService {
    @Autowired
    private ThreadRepository threadRepository;

    public List<Thread> getThreadsByForumId(UUID forumId) {
        return threadRepository.findAllByForumId(forumId);
    }

    public Thread getThreadById(UUID id) {
        return threadRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("thread", "id"));
    }

    public Thread createThread(UUID forumId, Thread thread) {
        var threadId = UUID.randomUUID();
        thread.setForumId(forumId);
        thread.setId(threadId);
        thread.getInitialPost().setThread(thread);

        return threadRepository.save(thread);

    }
}
