package com.client.manager.repository;

import com.client.manager.model.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository <Client,Long> {

}
