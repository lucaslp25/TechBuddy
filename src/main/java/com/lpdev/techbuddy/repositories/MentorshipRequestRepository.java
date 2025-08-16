package com.lpdev.techbuddy.repositories;

import com.lpdev.techbuddy.model.entities.MentorshipRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface MentorshipRequestRepository extends JpaRepository<MentorshipRequest, Long>{

    @Query("SELECT rq FROM MentorshipRequest rq WHERE rq.mentorRequested.id = :profileId OR rq.devRequester.id = :profileId")
    Set<MentorshipRequest> findAllRequestsByUserId(@Param("profileId") Long profileId);

}
