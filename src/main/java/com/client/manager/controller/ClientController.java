package com.client.manager.controller;

import com.client.manager.dto.ClientDto;
import com.client.manager.model.entity.Client;
import com.client.manager.service.interfaces.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private IClientService clientService;

    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody ClientDto clientDto) {
        Client createdClient = clientService.createClient(clientDto);
        return ResponseEntity.ok(createdClient);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @RequestBody ClientDto clientDto) {
        Client updatedClient = clientService.updateClient(id, clientDto);
        return ResponseEntity.ok(updatedClient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientService.deleteClientById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Long id) {
        Client client = clientService.getClientById(id);
        return ResponseEntity.ok(client);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Client>> getAllClients() {
        List<Client> clients = clientService.getAllClients();
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Client>> getClientByName(@RequestParam String name) {
        List<Client> clients = clientService.getClientByName(name);
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/search/ByLastName")
    public ResponseEntity<List<Client>> getClientByLastName(@RequestParam String lastName) {
        List<Client> clients = clientService.getClientByLastName(lastName);
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/search/ByEmail")
    public ResponseEntity<Client> getClientByEmail(@RequestParam String email) {
        Client client = clientService.getClientByEmail(email);
        return ResponseEntity.ok(client);
    }

    @GetMapping("/search/ByPhone")
    public ResponseEntity<Client> getClientByPhone(@RequestParam String phone) {
        Client client = clientService.getClientByPhone(phone);
        return ResponseEntity.ok(client);
    }
}