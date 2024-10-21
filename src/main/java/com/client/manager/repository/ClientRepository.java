package com.client.manager.repository;

import com.client.manager.model.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository <Client,Long> {

    Integer countByPhone (String phone);

    Integer countByEmail (String email);

    List<Client> findClientByEmail(String email);

    List<Client> findClientByName(String name);

    List<Client> findClientByLastName(String lastName);

}
