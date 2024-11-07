package com.eheiste.laureatnet.dto.modelview;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class LikeShowDTO {
	private Long id;
	private Long likerId;
	private String likerName;
}
