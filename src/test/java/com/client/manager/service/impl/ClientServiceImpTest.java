package com.client.manager.service.impl;

import com.client.manager.dto.ClientDto;
import com.client.manager.dto.DiscountDto;
import com.client.manager.exception.InvalidOperationException;
import com.client.manager.exception.ResourceNotFoundException;
import com.client.manager.mapper.ClientMapper;
import com.client.manager.mapper.DiscountMapper;
import com.client.manager.model.entity.Client;
import com.client.manager.model.entity.Discount;
import com.client.manager.model.enums.ClientStatus;
import com.client.manager.repository.ClientRepository;
import com.client.manager.repository.DiscountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceImpTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private DiscountRepository discountRepository;

    @Mock
    private ClientMapper clientMapper;

    @Mock
    private DiscountMapper discountMapper;

    @InjectMocks
    private ClientServiceImp clientService;

    private Client client;
    private ClientDto clientDto;
    private Discount discount;
    private DiscountDto discountDto;

    @BeforeEach
    void setUp() {
        client = new Client();
        client.setClientId(1L);
        client.setName("John");
        client.setLastName("Doe");
        client.setEmail("john.doe@example.com");
        client.setPhone("1234567890");
        client.setCreated(LocalDate.now());
        client.setUpdated(LocalDate.now());
        client.setState(ClientStatus.PREMIUM);

        clientDto = new ClientDto();
        clientDto.setClientId(1L);
        clientDto.setName("Pablo");
        clientDto.setLastName("Sarapura");
        clientDto.setEmail("john.doe@example.com");
        clientDto.setPhone("1234567890");
        clientDto.setCreated(LocalDate.now());
        clientDto.setUpdated(LocalDate.now());
        clientDto.setState(ClientStatus.PREMIUM);

        discount = new Discount();
        discount.setDiscoundId(1L);
        discount.setPercentage(10.0);

        discountDto = new DiscountDto();
        discountDto.setDiscountId(1L);
        discountDto.setPercentage(10.0);
    }

    @Test
    void createClient_Success() {
        when(clientRepository.findClientByPhone(anyString())).thenReturn(Optional.empty());
        when(clientRepository.findClientByEmail(anyString())).thenReturn(Optional.empty());
        when(clientRepository.save(any(Client.class))).thenReturn(client);

        Client createdClient = clientService.createClient(clientDto);

        assertNotNull(createdClient);
        assertEquals("John", createdClient.getName());
        verify(clientRepository, times(1)).save(any(Client.class));
    }

    @Test
    void createClient_ClientAlreadyExists() {
        when(clientRepository.findClientByPhone(anyString())).thenReturn(Optional.of(client));

        InvalidOperationException exception = assertThrows(InvalidOperationException.class, () -> {
            clientService.createClient(clientDto);
        });

        assertEquals("Client already exists", exception.getMessage());
        verify(clientRepository, times(0)).save(any(Client.class));
    }

    @Test
    void updateClient_Success() {
        when(clientRepository.findClientByClientId(anyLong())).thenReturn(Optional.of(client));
        when(clientRepository.save(any(Client.class))).thenReturn(client);

        Client updatedClient = clientService.updateClient(1L, clientDto);

        assertNotNull(updatedClient);
        assertEquals("Pablo", updatedClient.getName());
        assertEquals("Sarapura", updatedClient.getLastName());
        verify(clientRepository, times(1)).save(any(Client.class));
    }

    @Test
    void updateClient_ClientNotFound() {
        when(clientRepository.findClientByClientId(anyLong())).thenReturn(Optional.empty());

        InvalidOperationException exception = assertThrows(InvalidOperationException.class, () -> {
            clientService.updateClient(1L, clientDto);
        });

        assertEquals("Client not found", exception.getMessage());
        verify(clientRepository, times(0)).save(any(Client.class));
    }

    @Test
    void deleteClientById_Success() {
        when(clientRepository.findClientByClientId(anyLong())).thenReturn(Optional.of(client));

        clientService.deleteClientById(1L);

        verify(clientRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void deleteClientById_ClientNotFound() {
        when(clientRepository.findClientByClientId(anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            clientService.deleteClientById(1L);
        });

        assertEquals("Client", exception.getResourceName());
        assertEquals("id", exception.getFieldName());
        assertEquals(1L, exception.getFieldValue());
        verify(clientRepository, times(0)).deleteById(anyLong());
    }

    @Test
    void getClientById_Success() {
        when(clientRepository.findClientByClientId(anyLong())).thenReturn(Optional.of(client));

        Client foundClient = clientService.getClientById(1L);

        assertNotNull(foundClient);
        assertEquals("John", foundClient.getName());
        verify(clientRepository, times(1)).findClientByClientId(anyLong());
    }

    @Test
    void getClientById_ClientNotFound() {
        when(clientRepository.findClientByClientId(anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            clientService.getClientById(1L);
        });

        assertEquals("Client", exception.getResourceName());
        assertEquals("id", exception.getFieldName());
        assertEquals(1L, exception.getFieldValue());
        verify(clientRepository, times(1)).findClientByClientId(anyLong());
    }

    @Test
    void setDiscount_Success() {
        when(clientRepository.findClientByClientId(anyLong())).thenReturn(Optional.of(client));
        when(discountMapper.toEntity(any(DiscountDto.class))).thenReturn(discount);
        when(discountRepository.save(any(Discount.class))).thenReturn(discount);
        when(clientRepository.save(any(Client.class))).thenReturn(client);

        Client clientWithDiscount = clientService.setDiscount(1L, discountDto);

        assertNotNull(clientWithDiscount);
        assertEquals(10.0, clientWithDiscount.getDiscount().getPercentage());
        verify(discountRepository, times(1)).save(any(Discount.class));
        verify(clientRepository, times(1)).save(any(Client.class));
    }

    @Test
    void setDiscount_ClientNotFound() {
        when(clientRepository.findClientByClientId(anyLong())).thenReturn(Optional.empty());

        InvalidOperationException exception = assertThrows(InvalidOperationException.class, () -> {
            clientService.setDiscount(1L, discountDto);
        });

        assertEquals("Client not found", exception.getMessage());
        verify(discountRepository, times(0)).save(any(Discount.class));
        verify(clientRepository, times(0)).save(any(Client.class));
    }

    @Test
    void deleteDiscount_ClientNotFound() {
        when(clientRepository.findClientByClientId(anyLong())).thenReturn(Optional.empty());

        InvalidOperationException exception = assertThrows(InvalidOperationException.class, () -> {
            clientService.deleteDiscount(1L);
        });

        assertEquals("Client not found", exception.getMessage());
        verify(discountRepository, times(0)).deleteById(anyLong());
        verify(clientRepository, times(0)).save(any(Client.class));
    }

    @Test
    void uploadPhoto_Success() {
        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(client));
        when(clientRepository.save(any(Client.class))).thenReturn(client);

        Client clientWithPhoto = clientService.uploadPhoto(1L, new byte[]{1, 2, 3});

        assertNotNull(clientWithPhoto);
        assertArrayEquals(new byte[]{1, 2, 3}, clientWithPhoto.getPhoto());
        verify(clientRepository, times(1)).save(any(Client.class));
    }

    @Test
    void uploadPhoto_ClientNotFound() {
        when(clientRepository.findById(anyLong())).thenReturn(Optional.empty());

        InvalidOperationException exception = assertThrows(InvalidOperationException.class, () -> {
            clientService.uploadPhoto(1L, new byte[]{1, 2, 3});
        });

        assertEquals("Client not found", exception.getMessage());
        verify(clientRepository, times(0)).save(any(Client.class));
    }
}