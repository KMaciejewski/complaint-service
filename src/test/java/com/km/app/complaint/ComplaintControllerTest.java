package com.km.app.complaint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.km.app.complaint.dto.ComplaintPageResponse;
import com.km.app.complaint.dto.ComplaintRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ComplaintControllerTest {

    private final String BASE_COMPLAINT_PATH = "/api/complaints";
    private final ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;

    @Mock
    private ComplaintService complaintService;

    @InjectMocks
    private ComplaintController complaintController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(complaintController).build();
    }

    @Test
    void shouldAddComplaint() throws Exception {
        // given
        ComplaintRequest request = new ComplaintRequest("product-123", "Test content", "test-user");

        // when
        mockMvc.perform(post(BASE_COMPLAINT_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        // then
        verify(complaintService, times(1)).addComplaint(eq(request), anyString());
    }

    @Test
    void shouldUpdateContent() throws Exception {
        // given
        long complaintId = 1;
        String newContent = "Updated content";

        // when
        mockMvc.perform(patch(BASE_COMPLAINT_PATH + "/{id}/content", complaintId)
                        .param("newContent", newContent))
                .andExpect(status().isNoContent());

        // then
        verify(complaintService, times(1)).updateContent(complaintId, newContent);
    }

    @Test
    void shouldGetAllComplaints() throws Exception {
        // given
        int page = 1;
        int size = 10;
        ComplaintPageResponse response = new ComplaintPageResponse(List.of(), 0, 0, 0, 10);
        when(complaintService.getAll(page, size)).thenReturn(response);

        // when
        mockMvc.perform(get(BASE_COMPLAINT_PATH)
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.complaints").isArray());

        // then
        verify(complaintService, times(1)).getAll(page, size);
    }
}