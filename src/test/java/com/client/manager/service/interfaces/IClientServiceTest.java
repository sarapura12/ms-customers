package com.client.manager.service.interfaces;

import com.client.manager.dto.ClientDto;
import com.client.manager.mapper.ClientMapper;
import com.client.manager.model.entity.Client;
import com.client.manager.service.impl.ClientServiceImp;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class IClientServiceTest {

    @Autowired
    private IClientService clientService;
    @Autowired
    ClientMapper clientMapper;

    private ClientDto testClientDto;
    private ClientDto testClientDto2;
    private ClientDto testClientDto3;

    @BeforeEach
    void setUp() {
        testClientDto = new ClientDto();
        testClientDto.setName("Joaquin");
        testClientDto.setLastName("salas");
        testClientDto.setEmail("joaquinsalas@example.com");
        testClientDto.setPhone("1234567890");

        testClientDto2 = new ClientDto();
        testClientDto2.setName("Leonel");
        testClientDto2.setLastName("Caceres");
        testClientDto2.setEmail("joaquinsalas@example.com");
        testClientDto2.setPhone("9876543210");


        testClientDto3 = new ClientDto();
        testClientDto3.setName("Pablo");
        testClientDto3.setLastName("Sarapura");
        testClientDto3.setEmail("pablo@example.com");
        testClientDto3.setPhone("1234567890");
    }



    @AfterEach
    void tearDown() {
       /* List<Client> clients = clientService.getAllClients();
        for (Client client : clients) {
            System.out.println(client.toString());
            clientService.deleteClientById(client.getClientId());
        }*/
    }

    @Test
    void createClient() {
        System.out.println("createdClient");
        Client createdClient = clientService.createClient(testClientDto);
        assertNotNull(createdClient);
        assertEquals("Joaquin", createdClient.getName());

        clientService.deleteClientById(createdClient.getClientId());
    }

    @Test
    void updateClient() {
        System.out.println("updateClient");
        Client createdClient = clientService.createClient(testClientDto);
        testClientDto.setName("Jonatan");
        Client updatedClient = clientService.updateClient(createdClient.getClientId(), testClientDto);
        assertNotNull(updatedClient);
        assertEquals("Jonatan", updatedClient.getName());

        clientService.deleteClientById(createdClient.getClientId());
    }

    @Test
    void deleteClientById() {
        System.out.println("deleteClientById");
        Client createdClient = clientService.createClient(testClientDto);
        clientService.deleteClientById(createdClient.getClientId());
        assertThrows(RuntimeException.class, () -> clientService.getClientById(createdClient.getClientId()));
    }

    @Test
    void getClientById() {
        System.out.println("getClientById");
        Client createdClient = clientService.createClient(testClientDto);
        Client foundClient = clientService.getClientById(createdClient.getClientId());
        assertNotNull(foundClient);
        assertEquals(createdClient.getName(), foundClient.getName());

        clientService.deleteClientById(createdClient.getClientId());
    }

    @Test
    void getAllClients() {
        System.out.println("getAllClients");
        Client clientCreated = clientService.createClient(testClientDto);
        List<Client> clients = clientService.getAllClients();
        assertFalse(clients.isEmpty());

        clientService.deleteClientById(clientCreated.getClientId());
    }

    @Test
    void getClientByName() {
        System.out.println("getClientByName");
        Client createdCLient = clientService.createClient(testClientDto);
        List<Client> clients = clientService.getClientByName("Joaquin");
        assertFalse(clients.isEmpty());

        clientService.deleteClientById(createdCLient.getClientId());
    }

    @Test
    void getClientByLastName() {
        System.out.println("getClientByLastName");
        Client createdCLient =clientService.createClient(testClientDto);
        List<Client> clients = clientService.getClientByLastName("Salas");
        assertFalse(clients.isEmpty());

        clientService.deleteClientById(createdCLient.getClientId());
    }

    @Test
    void getClientByEmail() {
        System.out.println("getClientByEmail");
        Client createdCLient =clientService.createClient(testClientDto);
        Client client = clientService.getClientByEmail("joaquinsalas@example.com");
        assertNotNull(client);

        clientService.deleteClientById(createdCLient.getClientId());
    }


    @Test
    void notRepeatEmail () {
        System.out.println("notRepeatEmail");
        Client createdCLient =clientService.createClient(testClientDto);

        assertThrows(RuntimeException.class, () -> clientService.createClient(testClientDto2));
        clientService.deleteClientById(createdCLient.getClientId());

    }

    @Test
    void notRepeatPhone () {
        System.out.println("notRepeatPhone");
        Client createdCLient =clientService.createClient(testClientDto);

        assertThrows(RuntimeException.class, () -> clientService.createClient(testClientDto3));
        clientService.deleteClientById(createdCLient.getClientId());

    }
}