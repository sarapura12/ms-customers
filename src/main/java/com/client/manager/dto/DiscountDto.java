package com.client.manager.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiscountDto {
    private Long id;
    private Double percentage;
    private String expirationDate;
    private String discountType;
    private Integer clientId;

    public DiscountDto() {
        // Default constructor
    }

    public DiscountDto(Long id, Double percentage, String expirationDate, String discountType, Integer clientId) {
        this.id = id;
        this.percentage = percentage;
        this.expirationDate = expirationDate;
        this.discountType = discountType;
        this.clientId = clientId;
    }
}