package com.client.manager.model.entity;


import com.client.manager.model.enums.DiscountType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long discoundId;

    private Double percentage;
    @Temporal(TemporalType.DATE)
    private LocalDate expirationDate;

    @Enumerated(EnumType.STRING)
    private DiscountType discountType;


    @OneToOne
    @JoinColumn(name = "client_id")
    @JsonIgnore
    private Client client;

}
