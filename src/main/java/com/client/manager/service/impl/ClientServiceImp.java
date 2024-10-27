package com.client.manager.service.impl;

import com.client.manager.dto.ClientDto;
import com.client.manager.dto.DiscountDto;
import com.client.manager.exception.InvalidOperationException;
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


        if ((clientDto.getClientId()==null)&&(clientRepository.findClientByPhone(clientDto.getPhone()).isEmpty())
                &&(clientRepository.findClientByEmail(clientDto.getEmail()).isEmpty())){
            return clientRepository.save(clientMapper.toEntity(clientDto));
        }else{
            throw new InvalidOperationException("Client", clientDto.getClientId(), "Client already exists");
        }
    }

    @Override
    public Client updateClient(Long id, ClientDto clientDto) {

        clientRepository.findClientByPhone(clientDto.getPhone()).orElseThrow(() -> new InvalidOperationException("Client", clientDto.getPhone(), "Client already exists"));
        clientRepository.findClientByEmail(clientDto.getEmail()).orElseThrow(() -> new InvalidOperationException("Client", clientDto.getEmail(), "Client already exists"));
        clientRepository.findClientByClientId(id).orElseThrow(() -> new InvalidOperationException("Client", id, "Client not found"));

        clientDto.setClientId(id);
        return clientRepository.save(clientMapper.toEntity(clientDto));

    }

    @Override
    public void deleteClientById(Long id) {
        clientRepository.findClientByClientId(id).orElseThrow(() -> new InvalidOperationException("Client", id, "Client not found"));
        clientRepository.deleteById(id);
    }

    @Override
    public Client getClientById(Long id) {

        return clientRepository.findClientByClientId(id).orElseThrow(() -> new InvalidOperationException("Client", id, "Client not found"));
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
                .orElseThrow(() -> new InvalidOperationException("Client", email, "Client not found"))
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
    public Client setDiscount (Long clientId, DiscountDto discountDto){
        Client client = clientRepository.findClientByClientId(clientId)
                .map(List::of)
                .orElseThrow(() -> new InvalidOperationException("Client", clientId, "Client not found"))
                .get(0);

        Discount discountEntity=discountMapper.toEntity(discountDto);
        discountEntity.setClient(client);
        Discount discountSave = discountRepository.save(discountEntity);

        client.setDiscount(discountSave);


        return clientRepository.save(client);
    }

    @Transactional
    public Client deleteDiscount (Long clientId){
        Client client = clientRepository.findClientByClientId(clientId)
                .map(List::of)
                .orElseThrow(() -> new InvalidOperationException("Client", clientId, "Client not found"))
                .get(0);

        client.removeDiscount();
        return clientRepository.save(client);
    }



}
