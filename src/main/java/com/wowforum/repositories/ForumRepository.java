package com.wowforum.repositories;

import com.wowforum.entities.Forum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ForumRepository extends JpaRepository<Forum, UUID> {
}
