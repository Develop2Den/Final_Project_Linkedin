package com.finalProject.linkedin.dto.request.notification;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.finalProject.linkedin.utils.enums.TargetType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NotificationReq {

    private Long notificationId;
    @NotNull(message = "Recipient field must be filled ")
    private Long recipientId;
    @NotBlank(message = "Message can't be empty")
    private String message;
    private TargetType notificationType;
    private boolean read = false;
}
