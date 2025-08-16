package com.lpdev.techbuddy.repositories;

import com.lpdev.techbuddy.model.entities.MentorshipSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface MentorshipSessionRepository extends JpaRepository<MentorshipSession, Long> {

    @Query("SELECT ms FROM MentorshipSession ms WHERE ms.dev.id = :userId OR ms.mentor.id = :userId ORDER BY ms.scheduledAt DESC")
    Set<MentorshipSession> findAllSessionsByUserId(@Param("userId") Long userId);
}
