package com.client.manager.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientDto {
    private Integer clientId;
    private String name;
    private String lastName;
    private String email;
    private String phone;
    private byte[] photo;
    private String state;
    private String created;
    private String updated;
    private Long discountId;

    public ClientDto() {
        // Default constructor
    }

    public ClientDto(Integer clientId, String name, String lastName, String email, String phone, byte[] photo, String state, String created, String updated, Long discountId) {
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