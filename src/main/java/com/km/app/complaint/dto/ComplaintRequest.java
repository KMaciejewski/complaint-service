package com.km.app.complaint.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ComplaintRequest(@NotBlank @Size(max = 255) String productId,
                               @NotBlank @Size(max = 1000) String content,
                               @NotBlank @Size(max = 255) String reporter) {
}
