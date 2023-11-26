package com.intuit.complaintservice.service;

import com.intuit.complaintservice.dto.ComplaintRequest;
import com.intuit.complaintservice.dto.ComplaintResponse;
import com.intuit.complaintservice.model.Complaint;
import com.intuit.complaintservice.repository.ComplaintRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ComplaintServiceImpl implements ComplaintService {

    private final ComplaintRepository complaintRepository;

    public ComplaintResponse createComplaint(ComplaintRequest complaintRequest) {
        Complaint complaint = dtoToEntity(complaintRequest);
        complaintRepository.save(complaint);

        return createComplaintResponse(complaint);
    }

    public String getComplaint(UUID complaintId){
        Optional<Complaint> complaint = complaintRepository.findById(complaintId);
        if(complaint.isPresent()){
            return complaint.get().getContent();
        }
        throw new RuntimeException("not found");
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
}
