package com.km.app.complaint.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record ComplaintPageResponse(
        List<ComplaintResponse> complaints,
        long totalElements,
        int totalPages,
        int currentPage,
        int pageSize
) {
}
