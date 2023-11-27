package com.intuit.complaintservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intuit.complaintservice.dto.ComplaintRequest;
import com.intuit.complaintservice.dto.FullComplaintResponse;
import com.intuit.complaintservice.dto.PurchaseResponse;
import com.intuit.complaintservice.dto.UserManagementResponse;
import com.intuit.complaintservice.exception.ComplaintException;
import com.intuit.complaintservice.service.ComplaintService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ComplaintApplicationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ComplaintService complaintService;

    private final String COMPLAINT_URL = "/api/complaint";


    @Test
    public void testCreateComplaint_Status_Created() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(COMPLAINT_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getComplaintData())))
                .andExpect(status().isCreated());

    }

    @Test
    public void testCreateComplaint_Status_BadRequest() throws Exception {
        ComplaintRequest complaintData = getComplaintData();
        complaintData.setPurchaseId(null);

        mockMvc.perform(MockMvcRequestBuilders.post(COMPLAINT_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString((complaintData))))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetComplaint_Status_Ok() throws Exception {
        UUID complaintId = UUID.fromString("6d871ff5-8cb9-4513-ad76-4ad67c792e97");
        Mockito.when(complaintService.getComplaint(complaintId)).thenReturn(getComplaintResponse());

        mockMvc.perform(MockMvcRequestBuilders.get(COMPLAINT_URL + '/' + complaintId))
                .andExpect(status().isOk());

    }

    @Test
    public void testGetComplaint_Status_NotFound() throws Exception {
        ComplaintException ex = new ComplaintException("Complaint Doesn't exists!", HttpStatus.NOT_FOUND);
        UUID complaintId = UUID.fromString("6d871ff5-8cb9-4513-ad76-4ad67c792e98");
        Mockito.when(complaintService.getComplaint(complaintId)).thenThrow(ex);

        mockMvc.perform(MockMvcRequestBuilders.get(COMPLAINT_URL + '/' + complaintId))
                .andExpect(status().isNotFound());

    }

    private ComplaintRequest getComplaintData() {
        return ComplaintRequest.builder()
                .userId(UUID.fromString("14b28cf0-7a0d-11ec-90d6-0242ac120003"))
                .purchaseId(UUID.fromString("14b28cf0-7a0d-11ec-90d6-0242ac120003"))
                .subject("I made a purchase and the item hasn't shipped. It's been over a week. Please help!")
                .complaint("The seller never sent my item!")
                .build();
    }

    private FullComplaintResponse getComplaintResponse() {

        FullComplaintResponse fullResponse = FullComplaintResponse.builder()
                .complaintId(UUID.fromString("6d871ff5-8cb9-4513-ad76-4ad67c792e97"))
                .subject("I made a purchase and the item hasn't shipped. It's been over a week. Please help!")
                .content("The seller never sent my item!")
                .build();
        fullResponse.setUserManagementResponse(
                UserManagementResponse.builder()
                        .id(UUID.fromString("14b28cf0-7a0d-11ec-90d6-0242ac120003"))
                        .build()
        );

        fullResponse.setPurchaseResponse(
                PurchaseResponse.builder()
                        .id(UUID.fromString("14b28cf0-7a0d-11ec-90d6-0242ac120003"))
                        .build()
        );

        return fullResponse;

    }
}
