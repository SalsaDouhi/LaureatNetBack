package com.eheiste.laureatnet.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConnectionDTO {
    private Long id;
    private Long userConnectWithId;
    private Long userConnectedId;
    private boolean accepted;
    private String fullname;
    private String bio;
    private String picture;
}
