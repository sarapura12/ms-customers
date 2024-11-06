package com.client.manager.dto;

import com.client.manager.model.entity.Client;
import com.client.manager.model.enums.DiscountType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DiscountDto {
    private Long discountId;
    private Double percentage;
    private LocalDate expirationDate;
    private DiscountType discountType;
    private Long clientId;
    private Client client;

    public DiscountDto() {
        // Default constructor
    }

    public DiscountDto(Long discountId, Double percentage, LocalDate expirationDate, DiscountType discountType, Long clientId) {
        this.discountId = discountId;
        this.percentage = percentage;
        this.expirationDate = expirationDate;
        this.discountType = discountType;
        this.clientId = clientId;
    }
}