package com.lpdev.techbuddy.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_user_profiles")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "profile_type") //essa coluna vai identificar os tipos de perfils e redirecionar as info.
public abstract class UserProfile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Column(length = 100, unique = true, nullable = false)
    private String profileName;

    @Column(length = 150)
    private String headline; //texto localizado abaixo do nome e serve para resumir quem você é e o que faz.

    @Lob
    private String profileBio;

    private String profilePictureUrl;

    @Column(length = 100)
    private String profileLocation;

    private String profileLinkedinUrl;

    private String profileGithubUrl;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "tb_profile_stacks", joinColumns = @JoinColumn(name = "user_profile_id"))
    @Column(name = "stack_name", nullable = false)
    private Set<String> profileStacks = new HashSet<>();


    @OneToOne(fetch = FetchType.LAZY)
    @MapsId //mapeia para ser os mesmos ID
    @JoinColumn(name = "user_id")
    private User user;

    public UserProfile(User user) {
        this.user = user;
    }
}
