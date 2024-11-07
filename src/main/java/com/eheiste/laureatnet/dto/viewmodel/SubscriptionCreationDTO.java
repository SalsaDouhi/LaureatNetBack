package com.eheiste.laureatnet.dto.viewmodel;

import com.eheiste.laureatnet.model.UserAccount;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SubscriptionCreationDTO {
    @NotNull(message="mandatory")
    private Long subscriptionId;

    @NotNull(message="mandatory")
    private Long subscriberId;

    @NotNull(message="mandatory")
    private Long subscribedToId;

    @NotNull(message="mandatory")
    private LocalDateTime createdAt;
}
