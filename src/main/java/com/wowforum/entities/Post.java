package com.wowforum.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.wowforum.dtos.PostCreateDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "posts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User creator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Thread thread;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "created", nullable = false)
    private Long created;

    public Post (PostCreateDto postCreateDto) {
        this.content = postCreateDto.getContent();
        this.created = Instant.now().toEpochMilli();
    }
}
