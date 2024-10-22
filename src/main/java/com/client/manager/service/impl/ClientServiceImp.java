package com.client.manager.service.impl;

import com.client.manager.dto.ClientDto;
import com.client.manager.dto.DiscountDto;
import com.client.manager.exception.InvalidOperationException;
import com.client.manager.mapper.ClientMapper;
import com.client.manager.mapper.DiscountMapper;
import com.client.manager.model.entity.Client;
import com.client.manager.model.entity.Discount;
import com.client.manager.repository.ClientRepository;
import com.client.manager.service.interfaces.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImp implements IClientService {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ClientMapper clientMapper;
    @Autowired
    private DiscountMapper discountMapper;

    public Client createClient(ClientDto clientDto) {

        if ((clientRepository.countByPhone(clientDto.getPhone())==0) &&
                (clientRepository.countByEmail(clientDto.getEmail())==0)&&
                (clientDto.getClientId()==null)){
            return clientRepository.save(clientMapper.toEntity(clientDto));
        }else{
            throw new InvalidOperationException("Client", clientDto.getClientId(), "Client already exists");
        }
    }

    @Override
    public Client updateClient(Long id, ClientDto clientDto) {
        if ((clientRepository.countByPhone(clientDto.getPhone())==0) &&
                (clientRepository.countByEmail(clientDto.getEmail())==0)&&
                (clientRepository.countClientByClientId(id)!=0)){
            clientDto.setClientId(id);
            return clientRepository.save(clientMapper.toEntity(clientDto));
        }else{
            throw new InvalidOperationException("Client", clientDto.getClientId(), "Invalid customer data");
        }
    }

    @Override
    public void deleteClientById(Long id) {
        if (clientRepository.findByClientId(id) == null) {
            throw new InvalidOperationException("Client", id, "Client not found");
        }
        clientRepository.deleteById(id);
    }

    @Override
    public Client getClientById(Long id) {

        if(clientRepository.findByClientId(id) != null){
            return clientRepository.findByClientId(id);
        }else{
            throw new InvalidOperationException("Client", id, "Client not found");
        }
    }

    @Override
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public List<Client> getClientByName(String name) {
        return clientRepository.findClientByName(name);
    }

    @Override
    public List<Client> getClientByLastName(String lastName) {
        return clientRepository.findClientByLastName(lastName);
    }

    @Override
    public Client getClientByEmail(String email) {
        return clientRepository.findClientByEmail(email);
    }

    public Discount setDiscount (Long clientId, DiscountDto discountDto){
        Client client = clientRepository.findByClientId(clientId);
        if (client!=null){
            return client.setDiscount(discountMapper.toEntity(discountDto));
        }else{
            throw new InvalidOperationException("Client", clientId, "Client not found");
        }
    }

    public void deleteDiscount (Long clientId){
        Client client = clientRepository.findByClientId(clientId);
        if (client!=null && client.getDiscount()!=null){
            client.removeDiscount();
        }else{
            throw new InvalidOperationException("Client", clientId, "Client not found");
        }
    }


}
