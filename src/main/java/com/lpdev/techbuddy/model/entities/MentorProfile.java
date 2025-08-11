package com.lpdev.techbuddy.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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

    private boolean avaliableForMentoring; //true por padrão

    @Builder
    public MentorProfile(User user) {
        super(user);
        this.avaliableForMentoring = true;
    }

}
