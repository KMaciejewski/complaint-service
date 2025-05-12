package com.km.app.complaint.dto;

import jakarta.validation.constraints.NotBlank;

public record ComplaintRequest(@NotBlank String productId, @NotBlank String content, @NotBlank String reporter) {
}
