package com.km.app.complaint.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ComplaintResponse(
        long id,
        String productId,
        String content,
        LocalDateTime createdAt,
        String reporter,
        String country,
        int reportCount
) {
}
