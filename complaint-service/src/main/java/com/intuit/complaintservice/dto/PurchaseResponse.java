package com.intuit.complaintservice.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class PurchaseResponse {
    @JsonProperty("id")
    private UUID id;
    @JsonProperty("userId")
    private UUID userId;
    @JsonProperty("productId")
    private UUID productId;
    @JsonProperty("productName")
    private String productName;
    @JsonProperty("pricePaidAmount")
    private Double pricePaidAmount;
    @JsonProperty("priceCurrency")
    private String priceCurrency;
    @JsonProperty("discountPercent")
    private Double discountPercent;
    @JsonProperty("merchantId")
    private UUID merchantId;
}
