package com.wowforum.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wowforum.dtos.PostCreateDto;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "posts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Post implements Serializable {
  private static final long serialVersionUID = 1985140130623811996L;
  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  @Column(name = "id", updatable = false, nullable = false)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User creator;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JsonBackReference
  private Thread thread;

  @Column(name = "content", nullable = false)
  private String content;

  @Column(name = "created", nullable = false)
  private Long created;

  @Column(name = "is_warning")
  private boolean isWarning;

  public Post(PostCreateDto postCreateDto) {
    this.content = postCreateDto.getContent();
    this.created = Instant.now().toEpochMilli();
  }
}
