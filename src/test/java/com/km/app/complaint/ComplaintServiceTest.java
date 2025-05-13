package com.km.app.complaint;

import com.km.app.complaint.dto.ComplaintPageResponse;
import com.km.app.complaint.dto.ComplaintRequest;
import com.km.app.complaint.dto.ComplaintResponse;
import com.km.app.exception.ComplaintNotFoundException;
import com.km.app.location.LocationService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ComplaintServiceTest {

    private final String IP_ADDRESS = "8.8.8.8";
    private final long COMPLAINT_ID = 1;
    private final String NEW_CONTENT = "Updated content";
    private final String COUNTRY = "United States";
    private final String PRODUCT_ID = "product-123";
    private final String CURRENT_CONTENT = "Current content";
    private final String REPORTER = "test.user@gmail.com";

    @Mock
    private ComplaintRepository complaintRepository;

    @Mock
    private LocationService locationService;

    private ComplaintService complaintService;

    private AutoCloseable mocks;

    @BeforeEach
    void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
        complaintService = new ComplaintService(complaintRepository, locationService);
    }

    @AfterEach
    void tearDown() throws Exception {
        mocks.close();
    }

    @Test
    void shouldAddComplaintWhenNotExists() {
        // given
        ComplaintRequest request = getComplaintRequest();


        when(complaintRepository.findIdByProductIdAndReporter(request.productId(), request.reporter()))
                .thenReturn(Optional.empty());
        when(locationService.getCountryName(IP_ADDRESS)).thenReturn(COUNTRY);

        // when
        complaintService.addComplaint(request, IP_ADDRESS);

        // then
        ArgumentCaptor<Complaint> captor = ArgumentCaptor.forClass(Complaint.class);
        verify(complaintRepository).save(captor.capture());
        Complaint savedComplaint = captor.getValue();

        assertEquals(request.productId(), savedComplaint.getProductId());
        assertEquals(request.content(), savedComplaint.getContent());
        assertEquals(request.reporter(), savedComplaint.getReporter());
        assertEquals(COUNTRY, savedComplaint.getCountry());
        assertEquals(1, savedComplaint.getReportCount());
    }

    @Test
    void shouldIncrementReportCountWhenComplaintExists() {
        // given
        ComplaintRequest request = getComplaintRequest();
        long existingComplaintId = 123;

        when(complaintRepository.findIdByProductIdAndReporter(request.productId(), request.reporter()))
                .thenReturn(Optional.of(existingComplaintId));

        // when
        complaintService.addComplaint(request, IP_ADDRESS);

        // then
        verify(complaintRepository).incrementReportCount(existingComplaintId);
        verify(complaintRepository, never()).save(any());
    }

    @Test
    void shouldReturnAllComplaints() {
        // given
        int pageSize = 10;
        Complaint complaint = Complaint.builder()
                .id(COMPLAINT_ID)
                .productId(PRODUCT_ID)
                .content(CURRENT_CONTENT)
                .createdAt(LocalDateTime.now())
                .reporter(REPORTER)
                .country(COUNTRY)
                .reportCount(1)
                .build();
        Pageable pageable = PageRequest.of(0, pageSize, Sort.by("id").ascending());
        Page<Complaint> page = new PageImpl<>(List.of(complaint));

        when(complaintRepository.findAll(pageable)).thenReturn(page);

        // when
        ComplaintPageResponse pageResponse = complaintService.getAll(1, pageSize);

        // then
        assertEquals(1, pageResponse.complaints().size());
        ComplaintResponse response = pageResponse.complaints().getFirst();
        assertEquals(complaint.getId(), response.id());
        assertEquals(complaint.getProductId(), response.productId());
        assertEquals(complaint.getContent(), response.content());
        assertEquals(complaint.getCreatedAt(), response.createdAt());
        assertEquals(complaint.getReporter(), response.reporter());
        assertEquals(complaint.getCountry(), response.country());
        assertEquals(complaint.getReportCount(), response.reportCount());
    }

    @Test
    void shouldUpdateContentWhenComplaintExists() {
        // given
        when(complaintRepository.updateContent(COMPLAINT_ID, NEW_CONTENT)).thenReturn(1);

        // when
        complaintService.updateContent(COMPLAINT_ID, NEW_CONTENT);

        // then
        verify(complaintRepository).updateContent(COMPLAINT_ID, NEW_CONTENT);
    }

    @Test
    void shouldThrowExceptionWhenComplaintNotExist() {
        // given
        when(complaintRepository.updateContent(COMPLAINT_ID, NEW_CONTENT)).thenReturn(0);

        // when
        ComplaintNotFoundException exception = assertThrows(
                ComplaintNotFoundException.class,
                () -> complaintService.updateContent(COMPLAINT_ID, NEW_CONTENT)
        );

        // then
        assertEquals("Complaint with id 1 not found.", exception.getMessage());
        verify(complaintRepository).updateContent(COMPLAINT_ID, NEW_CONTENT);
    }

    private ComplaintRequest getComplaintRequest() {
        return new ComplaintRequest(PRODUCT_ID, CURRENT_CONTENT, REPORTER);
    }
}