package com.intuit.complaintservice.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseResponse {
    private UUID id;
    private UUID userId;
    private UUID productId;
    private String productName;
    private Double pricePaidAmount;
    private String priceCurrency;
    private Double discountPercent;
    private UUID merchantId;
    private LocalDate purchaseDate;
}
