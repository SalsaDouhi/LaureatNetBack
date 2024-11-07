package com.eheiste.laureatnet.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestDoctorate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean accepted;

    @ManyToOne
    @JoinColumn(name = "candidate_id")
    private UserAccount candidate;

    private LocalDateTime createdAt;
}
