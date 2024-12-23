package com.finalProject.linkedin.dto.responce.message;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageResp {
    private Long chatId;
    private Long messageId;
    private Long senderId;
    private Long recipientId;
    private String content;
    private LocalDateTime createdAt;
    private Boolean read;
}
