package com.km.app.complaint;

import com.km.app.complaint.dto.ComplaintPageResponse;
import com.km.app.complaint.dto.ComplaintRequest;
import com.km.app.complaint.dto.ComplaintResponse;
import com.km.app.exception.ComplaintNotFoundException;
import com.km.app.location.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
class ComplaintService {

    private final ComplaintRepository complaintRepository;
    private final LocationService locationService;

    @Transactional
    void addComplaint(ComplaintRequest request, String ipAddress) {
        Optional<Long> idOptional = complaintRepository.findIdByProductIdAndReporter(
                request.productId(),
                request.reporter()
        );

        if (idOptional.isPresent()) {
            long id = idOptional.get();
            complaintRepository.incrementReportCount(id);
        } else {
            String country = locationService.getCountryName(ipAddress);
            Complaint complaint = Complaint.builder()
                    .productId(request.productId())
                    .content(request.content())
                    .createdAt(LocalDateTime.now())
                    .reporter(request.reporter())
                    .country(country)
                    .reportCount(1)
                    .build();
            complaintRepository.save(complaint);
        }
    }

    ComplaintPageResponse getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("id").ascending());
        Page<Complaint> complaintPage = complaintRepository.findAll(pageable);
        return ComplaintMapper.toPageResponse(complaintPage);
    }

    @Transactional
    void updateContent(long id, String newContent) {
        int updated = complaintRepository.updateContent(id, newContent);
        if (updated == 0) {
            throw new ComplaintNotFoundException(id);
        }
    }
}
