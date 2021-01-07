package com.wowforum.entities;

import com.wowforum.dtos.BaseForumDto;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "forums")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Forum {
  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  @Column(name = "id", updatable = false, nullable = false)
  private UUID id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "description", nullable = false)
  private String description;

  @OneToMany
  @JoinColumn(name = "forum_id")
  private List<Thread> threads;

  @ManyToMany
  @JoinTable(
    name = "moderators",
    joinColumns = @JoinColumn(name = "forum_id"),
    inverseJoinColumns = @JoinColumn(name = "user_id"))
  private Set<User> moderators;

  public Forum (BaseForumDto forumDto) {
    this.name = forumDto.getName();
    this.description = forumDto.getDescription();
  }
}
