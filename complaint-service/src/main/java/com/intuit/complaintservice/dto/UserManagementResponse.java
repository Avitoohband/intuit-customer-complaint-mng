package com.intuit.complaintservice.dto;


import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserManagementResponse {
    private UUID id;
    private String fullName;
    private String emailAddress;
    private String physicalAddress;
}
