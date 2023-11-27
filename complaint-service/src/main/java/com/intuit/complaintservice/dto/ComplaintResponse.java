package com.intuit.complaintservice.dto;

import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ComplaintResponse {
    private UUID complaintId;
    private UUID userId;
    private UUID purchaseId;
    private String subject;
    private String content;

}
