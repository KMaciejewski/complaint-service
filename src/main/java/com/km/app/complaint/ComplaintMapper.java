package com.km.app.complaint;

import com.km.app.complaint.dto.ComplaintResponse;

class ComplaintMapper {

    static ComplaintResponse toDto(Complaint complaint) {
        return ComplaintResponse.builder()
                .id(complaint.getId())
                .productId(complaint.getProductId())
                .content(complaint.getContent())
                .createdAt(complaint.getCreatedAt())
                .reporter(complaint.getReporter())
                .country(complaint.getCountry())
                .reportCount(complaint.getReportCount())
                .build();
    }
}
