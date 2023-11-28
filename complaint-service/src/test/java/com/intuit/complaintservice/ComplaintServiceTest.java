package com.intuit.complaintservice;

import com.intuit.complaintservice.dto.ComplaintRequest;
import com.intuit.complaintservice.dto.FullComplaintResponse;
import com.intuit.complaintservice.dto.PurchaseResponse;
import com.intuit.complaintservice.dto.UserManagementResponse;
import com.intuit.complaintservice.model.Complaint;
import com.intuit.complaintservice.repository.ComplaintRepository;
import com.intuit.complaintservice.service.ComplaintServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ComplaintServiceTest {

    @InjectMocks
    private ComplaintServiceImpl complaintService;
    @Mock
    private ComplaintRepository complaintRepository;
    @Mock
    RestTemplate restTemplate;
    @Captor
    private ArgumentCaptor<Complaint> complaintArgumentCaptor;

    private final String PURCHASE_URI = "http://localhost:8081/purchases/{id}";
    private final String USER_MGN_URI = "http://localhost:8081/users/{id}";



    @Test
    public void testCreateComplaint() {
        ComplaintRequest complaintRequest = getComplaintRequest();
        Complaint expectedComplaint = getComplaint();
        complaintService.createComplaint(complaintRequest);

        verify(complaintRepository).save(complaintArgumentCaptor.capture());
        Complaint capturedComplaint = complaintArgumentCaptor.getValue();

        assertEquals(expectedComplaint.getUserId(), capturedComplaint.getUserId());
        assertEquals(expectedComplaint.getPurchaseId(), capturedComplaint.getPurchaseId());
        assertEquals(expectedComplaint.getSubject(), capturedComplaint.getSubject());
        assertEquals(expectedComplaint.getContent(), capturedComplaint.getContent());

    }

    @Test
    public void testGetComplaint() {
        UUID complaintId = UUID.fromString("577e7ad5-64a1-43de-8fe5-3bc0c1f67339");
        Complaint complaint = getComplaint();
        FullComplaintResponse expectedResponse = getFullResponse();

        when(complaintRepository.findById(complaintId)).thenReturn(Optional.of(complaint));

        when(restTemplate.getForObject(
                eq(PURCHASE_URI),
                eq(PurchaseResponse.class),
                eq(complaint.getPurchaseId().toString())
        )).thenReturn(getPurchaseResponse());

        when(restTemplate.getForObject(
                eq(USER_MGN_URI),
                eq(UserManagementResponse.class),
                eq(complaint.getUserId().toString())
        )).thenReturn(getUserManagementResponse());

        FullComplaintResponse  actualResponse = complaintService.getComplaint(complaintId);

        getComplaintAsserts(expectedResponse, actualResponse);


    }

    private Complaint getComplaint() {
        return Complaint.builder()
                .userId(UUID.fromString("a93adc57-4d59-4a9d-85c6-b5d48d99101d"))
                .purchaseId(UUID.fromString("f256c996-6dcb-40cf-8dce-a11fa9bcab6b"))
                .subject("I made a purchase and the item hasn't shipped. It's been over a week. Please help!")
                .content("The seller never sent my item!")
                .build();
    }

    private ComplaintRequest getComplaintRequest() {
        return ComplaintRequest.builder()
                .userId(UUID.fromString("a93adc57-4d59-4a9d-85c6-b5d48d99101d"))
                .purchaseId(UUID.fromString("f256c996-6dcb-40cf-8dce-a11fa9bcab6b"))
                .subject("I made a purchase and the item hasn't shipped. It's been over a week. Please help!")
                .complaint("The seller never sent my item!")
                .build();
    }
    private PurchaseResponse getPurchaseResponse(){
        return PurchaseResponse.builder()
                .id(UUID.fromString("f256c996-6dcb-40cf-8dce-a11fa9bcab6b"))
                .userId(UUID.fromString("a93adc57-4d59-4a9d-85c6-b5d48d99101d"))
                .productName("Test")
                .productId(UUID.fromString("4ac9da0b-66eb-415c-9153-a59ec0b3c3fe"))
                .productName("Book")
                .pricePaidAmount(13.2)
                .priceCurrency("USD")
                .discountPercent(0.1)
                .merchantId(UUID.fromString("71e234d2-dc65-41ff-bada-9d08d82fc6d1"))
                .build();
    }

    private UserManagementResponse getUserManagementResponse(){
        return UserManagementResponse.builder()
                .id(UUID.fromString("a93adc57-4d59-4a9d-85c6-b5d48d99101d"))
                .fullName("John Doe")
                .emailAddress("johndoe@test.com")
                .physicalAddress("Test Lane 1 Los Angeles")
                .build();
    }

    private FullComplaintResponse getFullResponse() {
        Complaint complaint = getComplaint();

        FullComplaintResponse fullResponse = FullComplaintResponse.builder()
                .complaintId(UUID.fromString("6d871ff5-8cb9-4513-ad76-4ad67c792e97"))
                .subject(complaint.getSubject())
                .content(complaint.getContent())
                .build();

        fullResponse.setPurchaseResponse(
                getPurchaseResponse()
        );

        fullResponse.setUserManagementResponse(
                getUserManagementResponse()
        );
        return fullResponse;

    }
    private static void getComplaintAsserts(FullComplaintResponse expectedResponse, FullComplaintResponse actualResponse) {
        assertEquals(expectedResponse.getSubject(), actualResponse.getSubject());
        assertEquals(expectedResponse.getPurchaseResponse().getProductName(),
                actualResponse.getPurchaseResponse().getProductName());
        assertEquals(expectedResponse.getUserManagementResponse().getFullName(),
                actualResponse.getUserManagementResponse().getFullName());
        // assert all fields...

    }
}
