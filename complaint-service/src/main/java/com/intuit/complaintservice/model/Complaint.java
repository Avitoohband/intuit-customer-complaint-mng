package com.intuit.complaintservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "t_complaint")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID complaintId;
    private UUID userId;
    private UUID purchaseId;
    private String subject;
    private String content;

}
