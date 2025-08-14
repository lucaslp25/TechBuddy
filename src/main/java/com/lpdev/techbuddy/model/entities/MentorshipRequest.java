package com.lpdev.techbuddy.model.entities;

import com.lpdev.techbuddy.model.enums.RequestStatus;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "tb_mentorship_requests")
public class MentorshipRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "dev_requester_id", nullable = false)
    private User devRequester;

    @ManyToOne
    @JoinColumn(name = "mentor_requested_id", nullable = false)
    private User mentorRequested;

    @Lob
    private String message;

    @Enumerated(EnumType.STRING)
    private RequestStatus requestStatus;

    private Instant createdAt;

    private Instant updatedAt;


    @PrePersist
    protected void onCreate() {
        createdAt = Instant.now();
    }
}
