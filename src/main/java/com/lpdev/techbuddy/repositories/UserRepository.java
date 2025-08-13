package com.lpdev.techbuddy.repositories;

import com.lpdev.techbuddy.model.entities.MentorProfile;
import com.lpdev.techbuddy.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByEmail(String email);

    @Query("SELECT mp FROM MentorProfile mp")
    Set<MentorProfile> findAllMentorProfiles();

    @Query("SELECT mp FROM MentorProfile mp WHERE mp.availableForMentoring = true")
    Set<MentorProfile> findAllAvailableMentorProfiles();

    @Query("SELECT mp FROM MentorProfile mp WHERE mp.profileName = :username")
    Optional<MentorProfile> findMentorProfileByUsername(@Param("username") String username);

    @Query("SELECT mp FROM MentorProfile mp JOIN mp.profileStacks s WHERE s = :stack")
    Set<MentorProfile> findAllMentorProfilesByStack(@Param("stack") String stack);
}

//depois separa as logicas para outros repositorios
