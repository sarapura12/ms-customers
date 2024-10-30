package com.client.manager.service.impl;

import com.client.manager.dto.ClientDto;
import com.client.manager.dto.DiscountDto;
import com.client.manager.exception.InvalidOperationException;
import com.client.manager.exception.ResourceNotFoundException;
import com.client.manager.mapper.ClientMapper;
import com.client.manager.mapper.DiscountMapper;
import com.client.manager.model.entity.Client;
import com.client.manager.model.entity.Discount;
import com.client.manager.repository.ClientRepository;
import com.client.manager.repository.DiscountRepository;
import com.client.manager.service.interfaces.IClientService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ClientServiceImp implements IClientService {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private DiscountRepository discountRepository;
    @Autowired
    private ClientMapper clientMapper;
    @Autowired
    private DiscountMapper discountMapper;

    public Client createClient(ClientDto clientDto) {

        var alreadyExist = clientRepository.findClientByPhone(clientDto.getPhone()).isPresent() || clientRepository.findClientByEmail(clientDto.getEmail()).isPresent();
        if (alreadyExist)
            throw new InvalidOperationException("Client", clientDto.getClientId(), "Client already exists");

        var newClient = new Client();
        newClient.setName(clientDto.getName());
        newClient.setLastName(clientDto.getLastName());
        newClient.setEmail(clientDto.getEmail());
        newClient.setPhone(clientDto.getPhone());
        newClient.setCreated(LocalDate.now());
        newClient.setState(clientDto.getState());
        clientRepository.save(newClient);

        return newClient;
    }

    @Override
    public Client updateClient(Long id, ClientDto clientDto) {
        var client = clientRepository.findClientByClientId(id).orElseThrow(() -> new InvalidOperationException("Client", id, "Client not found"));

        if (!client.getName().equals(clientDto.getName()))
            client.setName(clientDto.getName());

        if (!client.getLastName().equals(clientDto.getLastName()))
            client.setLastName(clientDto.getLastName());

        if (!client.getEmail().equals(clientDto.getEmail())) {
            var repeatEmail = clientRepository.findClientByEmail(clientDto.getEmail()).isPresent();
            if (repeatEmail)
                throw new InvalidOperationException("Client", clientDto.getEmail(), "Client already exists");

            client.setEmail(clientDto.getEmail());
        }

        if (!client.getPhone().equals(clientDto.getPhone())) {
            var repeatPhone = clientRepository.findClientByPhone(clientDto.getPhone()).isPresent();
            if (repeatPhone)
                throw new InvalidOperationException("Client", clientDto.getPhone(), "Client already exists");

            client.setPhone(clientDto.getPhone());
        }

        if (!client.getState().equals(clientDto.getState()))
            client.setState(clientDto.getState());

        client.setUpdated(LocalDate.now());

        clientDto.setUpdated(LocalDate.now());

        clientRepository.save(client);
        return client;
    }

    @Override
    public void deleteClientById(Long id) {
        clientRepository.findClientByClientId(id).orElseThrow(() -> new ResourceNotFoundException("Client", "id", id));
        clientRepository.deleteById(id);
    }

    @Override
    public Client getClientById(Long id) {

        return clientRepository.findClientByClientId(id).orElseThrow(() -> new ResourceNotFoundException("Client", "id", id));
    }

    @Override
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public List<Client> getClientByName(String name) {
        return clientRepository.findClientByName(name).stream().toList();
    }

    @Override
    public List<Client> getClientByLastName(String lastName) {
        return clientRepository.findClientByLastName(lastName).stream().toList();
    }

    @Override
    public Client getClientByEmail(String email) {
        return clientRepository.findClientByEmail(email)
                .map(List::of)
                .orElseThrow(() -> new ResourceNotFoundException("Client", "email", email))
                .get(0);

    }

    @Override
    public Client getClientByPhone(String phone) {
        return clientRepository.findClientByPhone(phone)
                .map(List::of)
                .orElseThrow(() -> new InvalidOperationException("Client", phone, "Client not found"))
                .get(0);
    }

    @Transactional
    public Client setDiscount(Long clientId, DiscountDto discountDto) {
        Client client = clientRepository.findClientByClientId(clientId)
                .map(List::of)
                .orElseThrow(() -> new InvalidOperationException("Client", clientId, "Client not found"))
                .get(0);

        Discount discountEntity = discountMapper.toEntity(discountDto);
        discountEntity.setClient(client);
        Discount discountSave = discountRepository.save(discountEntity);

        client.setDiscount(discountSave);


        return clientRepository.save(client);
    }

    @Transactional
    public Client deleteDiscount(Long clientId) {
        Client client = clientRepository.findClientByClientId(clientId)
                .map(List::of)
                .orElseThrow(() -> new InvalidOperationException("Client", clientId, "Client not found"))
                .get(0);

        Long value = client.getDiscount().getDiscoundId();

        client.removeDiscount();

        discountRepository.deleteById(value);
        return clientRepository.save(client);
    }

    @Transactional
    @Override
    public Client uploadPhoto(Long id, byte[] photo) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new InvalidOperationException("Client", id, "Client not found"));
        client.setPhoto(photo);
        return clientRepository.save(client);
    }


}
