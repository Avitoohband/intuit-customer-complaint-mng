package com.intuit.complaintservice.dto;

import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class FullComplaintResponse {
    private UUID complaintId;
    private String subject;
    private String content;
    private PurchaseResponse purchaseResponse;
    private UserManagementResponse userManagementResponse;
}
