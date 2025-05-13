package com.km.app.complaint;

import com.km.app.complaint.dto.ComplaintPageResponse;
import com.km.app.complaint.dto.ComplaintResponse;
import org.springframework.data.domain.Page;

import java.util.List;

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

    static ComplaintPageResponse toPageResponse(Page<Complaint> page) {
        List<ComplaintResponse> complaints = page.stream()
                .map(ComplaintMapper::toDto)
                .toList();

        return ComplaintPageResponse.builder()
                .complaints(complaints)
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .currentPage(page.getNumber() + 1)
                .pageSize(page.getSize())
                .build();
    }
}
