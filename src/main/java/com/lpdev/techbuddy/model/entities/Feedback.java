package com.lpdev.techbuddy.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_feedback")
public class Feedback implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer rating;

    @Lob
    private String comment;

    @OneToOne
    @JoinColumn(name = "session_id", nullable = false, unique = true)
    private MentorshipSession session;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User dev;

    @ManyToOne
    @JoinColumn(name = "target_id")
    private User mentor;

    private Instant createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = Instant.now();
    }
}
