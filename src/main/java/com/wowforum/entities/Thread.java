package com.wowforum.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wowforum.dtos.ThreadCreateDto;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "threads")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Thread implements Serializable {
  private static final long serialVersionUID = 6424799699130458048L;
  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  @Column(name = "id", updatable = false, nullable = false)
  private UUID id;

  @Column(name = "forum_id", nullable = false)
  private UUID forumId;

  @Column(name = "topic", nullable = false)
  private String topic;

  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private Post initialPost;

  @OneToMany(mappedBy = "thread", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private List<Post> posts;

  @Column(name = "is_locked", columnDefinition = "int default 0")
  private boolean isLocked;

  public Thread(ThreadCreateDto threadCreateDto, UUID forumId, User creator) {
    this.id = UUID.randomUUID();
    this.forumId = forumId;
    this.topic = threadCreateDto.getTopic();
    this.initialPost = new Post(threadCreateDto.getInitialPost());
    this.initialPost.setThread(this);
    this.initialPost.setCreator(creator);
    this.posts = List.of(this.initialPost);
  }
}
