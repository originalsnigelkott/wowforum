package com.wowforum.services;

import com.wowforum.entities.Thread;
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

    public Thread createThread(UUID forumId, Thread thread) {
        thread.setForumId(forumId);
        return threadRepository.save(thread);
    }
}
