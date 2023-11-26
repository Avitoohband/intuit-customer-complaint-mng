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
public class UserManagementResponse {
    private UUID id;
    private String fullName;
    private String emailAddress;
    private String physicalAddress;
}
