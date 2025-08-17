package com.lpdev.techbuddy.repositories;

import com.lpdev.techbuddy.model.entities.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    //pegar a media de nota de um mentor
    @Query("SELECT AVG(f.rating) FROM Feedback f WHERE f.mentor.id = :mentorId")
    Double findAverageRatingByMentorId(@Param("mentorId") Long mentorId);

    @Query("SELECT COUNT(f) FROM Feedback f WHERE f.mentor.id = :mentorId")
    Long countFeedbackByMentorId(@Param("mentorId") Long mentorId);

}
