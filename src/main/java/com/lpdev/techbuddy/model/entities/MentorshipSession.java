package com.lpdev.techbuddy.model.entities;

import com.lpdev.techbuddy.model.enums.SessionStatus;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "tb_mentorship_sessions")
public class MentorshipSession implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "request_id", nullable = false, unique = true)
    private MentorshipRequest mentorshipRequest;

    @ManyToOne
    @JoinColumn(name = "dev_id", nullable = false)
    private User dev;

    @ManyToOne
    @JoinColumn(name = "mentor_id", nullable = false)
    private User mentor;

    private String topic;
    private Instant scheduledAt;
    private Duration duration;
    private String platformUrl;

    @Enumerated(EnumType.STRING)
    private SessionStatus status;

}
