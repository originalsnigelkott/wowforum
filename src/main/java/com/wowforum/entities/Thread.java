package com.wowforum.entities;

import com.wowforum.dtos.ThreadCreateDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "threads")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Thread {
  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  @Column(name = "id", updatable = false, nullable = false)
  private UUID id;

  @Column(name = "forum_id", nullable = false)
  private UUID forumId;

  @Column(name = "topic", nullable = false)
  private String topic;

  @OneToOne(cascade = CascadeType.ALL)
  private Post initialPost;

  @OneToMany(mappedBy = "thread", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Post> posts;

  public Thread(ThreadCreateDto threadCreateDto, UUID forumId, User creator) {
    this.id = UUID.randomUUID();
    this.forumId = forumId;
    this.topic = threadCreateDto.getTopic();
    this.initialPost = new Post(threadCreateDto.getInitialPost());
    this.initialPost.setThread(this);
    this.initialPost.setCreator(creator);
  }
}
