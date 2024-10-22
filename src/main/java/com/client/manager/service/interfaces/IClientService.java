package com.client.manager.service.interfaces;

import com.client.manager.dto.ClientDto;
import com.client.manager.model.entity.Client;

import java.util.List;

public interface IClientService {

    Client createClient(ClientDto client);

    Client updateClient(Long id, ClientDto client);

    void deleteClientById(Long id);

    Client getClientById(Long id);

    List<Client> getAllClients();

    List<Client> getClientByName(String name);

    List<Client> getClientByLastName(String lastName);

    Client getClientByEmail(String email);

}
