package com.intuit.complaintservice.dto;


import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ComplaintRequest {
    private UUID userId;
    private UUID purchaseId;
    private String subject;
    private String complaint;
}
