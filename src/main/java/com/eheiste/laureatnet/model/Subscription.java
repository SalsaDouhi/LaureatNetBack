package com.eheiste.laureatnet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean accepted;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "subscriber_id")
    private UserAccount subscriber;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "subscribed_to_id")
    private UserAccount subscribedTo;

    private LocalDateTime createdAt;
}
