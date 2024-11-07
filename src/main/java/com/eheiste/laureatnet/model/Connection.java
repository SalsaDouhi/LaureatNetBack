package com.eheiste.laureatnet.model;

import com.fasterxml.jackson.annotation.*;
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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Connection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean accepted;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "sender_id")
    private UserAccount sender;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "receiver_id")
    private UserAccount receiver;
    private LocalDateTime createdAt;
}
