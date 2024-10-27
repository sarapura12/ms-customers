package com.client.manager.service.interfaces;

import com.client.manager.dto.ClientDto;
import com.client.manager.dto.DiscountDto;
import com.client.manager.model.entity.Client;
import com.client.manager.model.entity.Discount;

import java.util.List;

public interface IClientService {

    Client createClient(ClientDto client);

    Client updateClient(Long id, ClientDto client);

    void deleteClientById(Long id);

    List<Client> getAllClients();

    List<Client> getClientByName(String name);

    List<Client> getClientByLastName(String lastName);

    Client getClientByEmail(String email);

    Client getClientById(Long id);

    Client getClientByPhone(String phone);

    Client setDiscount(Long id, DiscountDto discount);

    Client deleteDiscount(Long id);

}
