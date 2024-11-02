package com.client.manager.controller;

import com.client.manager.dto.ClientDto;
import com.client.manager.dto.DiscountDto;
import com.client.manager.model.entity.Client;
import com.client.manager.service.interfaces.IClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {
    private final IClientService clientService;

    public ClientController(IClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/new/")
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

    @PostMapping("/{clientId}/discount")
    public ResponseEntity<Client> setDiscount(@PathVariable Long clientId, @RequestBody DiscountDto discountDto) {
        Client client = clientService.setDiscount(clientId, discountDto);
        return ResponseEntity.ok(client);
    }

    @DeleteMapping("/{clientId}/discount")
    public ResponseEntity<Client> deleteDiscount(@PathVariable Long clientId) {
        Client client = clientService.deleteDiscount(clientId);
        return ResponseEntity.ok(client);
    }

    @PostMapping("/{clientId}/uploadPhoto")
    public ResponseEntity<String> uploadPhoto(@PathVariable Long clientId, @RequestParam("photo") MultipartFile photo) {
        try {
            byte[] photoBytes = photo.getBytes();
            Client client = clientService.uploadPhoto(clientId, photoBytes);
            client.setPhoto(null);
            return ResponseEntity.ok("Photo uploaded successfully");
            //return ResponseEntity.ok(client);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}