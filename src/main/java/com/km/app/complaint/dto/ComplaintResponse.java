package com.km.app.complaint.dto;

import java.time.LocalDateTime;

public record ComplaintResponse(
        String id,
        String productId,
        String content,
        LocalDateTime createdAt,
        String reporter,
        String country,
        int reportCount
) {
}
