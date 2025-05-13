package com.km.app.complaint;

import com.km.app.complaint.dto.ComplaintRequest;
import com.km.app.complaint.dto.ComplaintResponse;
import com.km.app.location.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
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

    List<ComplaintResponse> getAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by("id").ascending());
        return complaintRepository.findAll(pageRequest)
                .stream()
                .map(complaint -> new ComplaintResponse(
                        complaint.getId(),
                        complaint.getProductId(),
                        complaint.getContent(),
                        complaint.getCreatedAt(),
                        complaint.getReporter(),
                        complaint.getCountry(),
                        complaint.getReportCount()
                )).toList();
    }

    @Transactional
    void updateContent(long id, String newContent) {
        int updated = complaintRepository.updateContent(id, newContent);
        if (updated == 0) {
            // TODO add proper exception handling
            throw new ComplaintNotFoundException(id);
        }
    }
}
