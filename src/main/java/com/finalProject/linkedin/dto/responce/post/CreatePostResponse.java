package com.finalProject.linkedin.dto.responce.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreatePostResponse(
        @NotNull(message = "Should be not empty")
        Long authorId,
        @NotNull(message = "Should be not empty")
        String title,
        @NotBlank(message = "Should be not empty")
        String content
) {
}
