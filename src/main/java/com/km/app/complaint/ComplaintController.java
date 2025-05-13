package com.km.app.complaint;

import com.km.app.complaint.dto.ComplaintRequest;
import com.km.app.complaint.dto.ComplaintResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/complaints")
@RequiredArgsConstructor
class ComplaintController {

    private final ComplaintService complaintService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void addComplaint(@RequestBody @Valid ComplaintRequest complaintRequest, HttpServletRequest httpServletRequest) {
        String ip = httpServletRequest.getRemoteAddr();
        complaintService.addComplaint(complaintRequest, ip);
    }

    @PatchMapping("/{id}/content")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void updateContent(@PathVariable Long id, String newContent) {
        complaintService.updateContent(id, newContent);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ComplaintResponse> getAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {
        return complaintService.getAll(page, size);
    }
}
