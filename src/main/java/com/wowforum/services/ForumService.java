package com.wowforum.services;

import com.wowforum.entities.Forum;
import com.wowforum.repositories.ForumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ForumService {
    @Autowired
    private ForumRepository forumRepository;

    public List<Forum> getAllForums() {
        return forumRepository.findAll();
    }

    public Forum createForum(Forum forum) {
        return forumRepository.save(forum);
    }
}
