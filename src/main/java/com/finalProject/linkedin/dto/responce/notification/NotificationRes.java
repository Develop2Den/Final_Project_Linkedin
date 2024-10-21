package com.finalProject.linkedin.dto.responce.notification;


import com.finalProject.linkedin.utils.enums.TargetType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationRes {

    private Long notificationId;
    private Long recipientId;
    private String message;
    private TargetType notificationType;
    private LocalDateTime createdAt;
    private Boolean read;

}
