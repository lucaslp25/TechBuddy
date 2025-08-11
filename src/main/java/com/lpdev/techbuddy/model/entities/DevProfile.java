package com.lpdev.techbuddy.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_dev_profiles")
@DiscriminatorValue("DEV")  //Valor que será salvo na coluna 'profile_type'
public class DevProfile extends UserProfile implements Serializable{

    private static final long serialVersionUID = 1L;

    @Lob
    private String learningGoals; //metas de aprendizado

    @Column(length = 50)
    private String currentSkillsLevel; //nivel atual. ex: junior, estudante... etc

    @Builder
    public DevProfile(User user) {
        super(user);
    }

}
