package com.intuit.complaintservice.service;

import com.intuit.complaintservice.dto.ComplaintRequest;
import com.intuit.complaintservice.dto.ComplaintResponse;
import com.intuit.complaintservice.dto.FullComplaintResponse;

import java.util.UUID;

public interface ComplaintService {
    ComplaintResponse createComplaint(ComplaintRequest complaintRequest);
    FullComplaintResponse getComplaint(UUID complaintId);

}
