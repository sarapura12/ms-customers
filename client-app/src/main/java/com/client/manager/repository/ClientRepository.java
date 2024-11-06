package com.client.manager.repository;

import com.client.manager.model.entity.Client;
import com.client.manager.model.entity.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository <Client,Long> {

    Optional <Client> findClientByPhone (String phone);

    Optional <Client> findClientByEmail (String email);

    Optional <Client> findClientByClientId(Long clientId);

    Optional <Client> findClientByName(String name);

    Optional <Client> findClientByLastName(String lastName);

    //Discount setDiscountIdByClientId(Long clientId, Long discountId);
}
