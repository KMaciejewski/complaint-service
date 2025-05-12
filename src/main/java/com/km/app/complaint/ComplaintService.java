package com.km.app.complaint;

import com.km.app.complaint.dto.ComplaintRequest;
import com.km.app.complaint.dto.ComplaintResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
class ComplaintService {

    private final ComplaintRepository complaintRepository;

    void addOrUpdate(ComplaintRequest request, String ip) {
        Optional<Complaint> complaintOptional = complaintRepository.findByProductIdAndReporter(
                request.productId(),
                request.reporter()
        );

        if (complaintOptional.isPresent()) {
            Complaint complaint = complaintOptional.get();
            complaint.setReportCount(complaint.getReportCount() + 1);
        } else {
            String country = ""; // TODO get country from ip
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
                        complaint.getId().toString(),
                        complaint.getProductId(),
                        complaint.getContent(),
                        complaint.getCreatedAt(),
                        complaint.getReporter(),
                        complaint.getCountry(),
                        complaint.getReportCount()
                )).toList();
    }

    public void updateContent(Long id, String content) {
        Complaint complaint = complaintRepository.findById(id).orElseThrow(() -> new ComplaintNotFoundException(id));
        complaint.setContent(content);
        complaintRepository.save(complaint); // TODO Maybe patch instead of PUT
    }
}
