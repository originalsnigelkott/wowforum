package com.wowforum.services;

import com.wowforum.configs.MyUserDetailsService;
import com.wowforum.dtos.PostCreateDto;
import com.wowforum.entities.Post;
import com.wowforum.exceptions.EntityNotFoundException;
import com.wowforum.repositories.PostRepository;
import com.wowforum.repositories.ThreadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class PostService {
  @Autowired
  private PostRepository postRepository;

  @Autowired
  private MyUserDetailsService userDetailsService;

  @Autowired
  private ThreadRepository threadRepository;

  public List<Post> getPostsByThreadId(UUID threadId) {
    return postRepository.findAllByThreadId(threadId);
  }

  public Post getPostById(UUID id) {
    return postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("post", "id"));
  }

  public Post createPost(UUID threadId, PostCreateDto postDto) {
    var thread = threadRepository.findById(threadId).orElseThrow(() -> new EntityNotFoundException("thread", "id"));
    var creator = userDetailsService.getCurrentUser();
    if (creator == null) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Need to be logged in to complete this request.");
    }
    var post = new Post(postDto);
    post.setThread(thread);
    post.setCreator(creator);
    return postRepository.save(post);
  }
}
