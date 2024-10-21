package com.client.manager.model.entity;


import com.client.manager.model.enums.ClientStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clientId;
    private String name;
    private String lastName;
    private String email;
    private String phone;

    @Lob
    private byte[] photo;

    @Enumerated(EnumType.STRING)
    private ClientStatus state;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDate created;
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDate updated;

    @OneToOne(mappedBy = "client", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private Discount discount;

    public void setDiscount(Discount discount) {
        this.discount = discount;
        if (discount != null) {
            discount.setClient(this);
        }
    }

    public void removeDiscount() {
        if (this.discount != null) {
            this.discount.setClient(null);
            this.discount = null;
        }
    }



}
