package com.client.manager.repository;

import com.client.manager.model.entity.Client;
import com.client.manager.model.entity.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository <Client,Long> {

    Integer countByPhone (String phone);

    Integer countByEmail (String email);

    Integer countClientByClientId(Long clientId);

    Client findClientByEmail(String email);

    List<Client> findClientByName(String name);

    List<Client> findClientByLastName(String lastName);

    Client findByClientId (Long clientId);

    Discount setDiscountIdByClientId(Long clientId, Long discountId);
}
