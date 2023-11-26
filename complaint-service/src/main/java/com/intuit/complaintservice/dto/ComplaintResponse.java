package com.intuit.complaintservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ComplaintResponse {
    private UUID complaintId;
    private UUID userId;
    private UUID purchaseId;
    private String subject;
    private String content;

}
