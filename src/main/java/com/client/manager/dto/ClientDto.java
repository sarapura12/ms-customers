package com.client.manager.dto;

import com.client.manager.model.enums.ClientStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ClientDto {
    private Long clientId;
    private String name;
    private String lastName;
    private String email;
    private String phone;
    private byte[] photo;
    private ClientStatus state;
    private LocalDate created;
    private LocalDate updated;
    private Long discountId;

    public ClientDto() {
        // Default constructor
    }

    public ClientDto(Long clientId, String name, String lastName, String email, String phone, byte[] photo, ClientStatus state, LocalDate created, LocalDate updated, Long discountId) {
        this.clientId = clientId;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.photo = photo;
        this.state = state;
        this.created = created;
        this.updated = updated;
        this.discountId = discountId;
    }
}