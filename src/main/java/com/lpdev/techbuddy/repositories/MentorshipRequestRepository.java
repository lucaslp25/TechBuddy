package com.lpdev.techbuddy.repositories;

import com.lpdev.techbuddy.model.entities.MentorshipRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MentorshipRequestRepository extends JpaRepository<MentorshipRequest, Long> {
}
