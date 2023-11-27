package com.intuit.complaintservice.service;

import com.intuit.complaintservice.dto.*;
import com.intuit.complaintservice.exception.ComplaintException;
import com.intuit.complaintservice.model.Complaint;
import com.intuit.complaintservice.repository.ComplaintRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ComplaintServiceImpl implements ComplaintService {

    private final ComplaintRepository complaintRepository;

    public ComplaintResponse createComplaint(ComplaintRequest complaintRequest) {
        Complaint complaint = dtoToEntity(complaintRequest);
        complaintRepository.save(complaint);
        log.info("Complaint {} is saved!", complaint);

        return createComplaintResponse(complaint);
    }

    public FullComplaintResponse getComplaint(UUID complaintId) {
        Optional<Complaint> complaint = complaintRepository.findById(complaintId);
        if (complaint.isPresent()) {
            PurchaseResponse purchase = getPurchases(complaint.get().getPurchaseId());
            UserManagementResponse user = getUserManagement(complaint.get().getUserId());
            return createFullResponse(complaint.get(), purchase, user);
        }
        log.error("Complaint with id {} doesn't exists", complaintId);
        throw new ComplaintException("Complaint Doesn't exists!", HttpStatus.NOT_FOUND);
    }


    private Complaint dtoToEntity(ComplaintRequest complaintRequest) {
        return Complaint.builder()
                .userId(complaintRequest.getUserId())
                .purchaseId(complaintRequest.getPurchaseId())
                .subject(complaintRequest.getSubject())
                .content(complaintRequest.getComplaint())
                .build();
    }

    private ComplaintResponse createComplaintResponse(Complaint complaint) {
        return ComplaintResponse.builder()
                .complaintId(complaint.getComplaintId())
                .userId(complaint.getUserId())
                .purchaseId(complaint.getPurchaseId())
                .subject(complaint.getSubject())
                .content(complaint.getContent())
                .build();
    }

    private FullComplaintResponse createFullResponse(Complaint complaint, PurchaseResponse purchase, UserManagementResponse user) {
        return FullComplaintResponse.builder()
                .complaintId(complaint.getComplaintId())
                .subject(complaint.getSubject())
                .content(complaint.getContent())
                .purchaseResponse(purchase)
                .userManagementResponse(user)
                .build();
    }

    private PurchaseResponse getPurchases(UUID purchaseId) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject("http://localhost:8081/purchases/{id}", PurchaseResponse.class, purchaseId.toString());
    }

    private UserManagementResponse getUserManagement(UUID userId) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject("http://localhost:8081/users/{id}", UserManagementResponse.class, userId.toString());
    }

}
