package com.eheiste.laureatnet.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "conversation_id")
    private Conversation conversation;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private UserAccount sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private UserAccount receiver;

    private String content;

    @OneToMany
    private List<MessageAttachment> messageAttachments;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
