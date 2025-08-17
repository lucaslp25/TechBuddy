package com.lpdev.techbuddy.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_mentor_profiles")
@DiscriminatorValue("MENTOR")  //Valor que será salvo na coluna 'profile_type'
public class MentorProfile extends UserProfile implements Serializable{

    private static final long serialVersionUID = 1L;

    private Integer experienceYears;

    @Column(length = 100)
    private String company;

    @Column(length = 100)
    private String professionalTitle; // ex: Back-end Java Senior

    private boolean availableForMentoring; //true por padrão

    private Double averageRating = 0.0;

    private Long totalMentorships = 0L;

    @Builder
    public MentorProfile(User user) {
        super(user);
        this.availableForMentoring = true;
        this.averageRating = 0.0;
        this.totalMentorships = 0L;
    }

    @PrePersist
    public void prePersist(){
        averageRating = 0.0;
        totalMentorships = 0L;
    }

}
