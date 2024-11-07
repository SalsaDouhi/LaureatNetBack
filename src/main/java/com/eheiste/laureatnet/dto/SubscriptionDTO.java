package com.eheiste.laureatnet.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionDTO {
    private Long id;
    private Long subscribedToId;
    private Long subscriberId;
    private boolean accepted;
    private String fullname;
    private String bio;
    private String picture;
}
