package com.client.manager.service.interfaces;

import com.client.manager.dto.ClientDto;
import com.client.manager.dto.DiscountDto;
import com.client.manager.model.entity.Client;
import com.client.manager.model.entity.Discount;
import com.client.manager.model.enums.DiscountType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class IDiscountServiceTest {

    @Autowired
    private IDiscountService discountService;
    @Autowired
    private IClientService clientService;

    private ClientDto testClientDto;
    private ClientDto testClientDto2;

    private DiscountDto testDiscountDto;
    private DiscountDto testDiscountDto2;

    private Client ResultFinal;
    private Client ResultFinal2;


    @BeforeEach
    void setUp() {
        testClientDto = new ClientDto();
        testClientDto.setName("Joaquin");
        testClientDto.setLastName("salas");
        testClientDto.setEmail("jonatansalas@gmail.com");
        testClientDto.setPhone("1234567890");

        testClientDto2 = new ClientDto();
        testClientDto2.setName("Leonel");
        testClientDto2.setLastName("Caceres");
        testClientDto2.setEmail("leonelcaceres@gmail.com");
        testClientDto.setPhone("9876543210");

        testDiscountDto = new DiscountDto();
        testDiscountDto.setDiscountType(DiscountType.FIXED);
        testDiscountDto.setPercentage(20D);

        testDiscountDto2 = new DiscountDto();
        testDiscountDto2.setDiscountType(DiscountType.COUPON);
        testDiscountDto2.setPercentage(30D);

        Client clientResult= clientService.createClient(testClientDto);
        Client clientResult2=clientService.createClient(testClientDto2);

        ResultFinal=clientService.setDiscount(clientResult.getClientId(), testDiscountDto);
        ResultFinal2=clientService.setDiscount(clientResult2.getClientId(), testDiscountDto2);

    }

    @AfterEach
    void tearDown() {

        List<Client> clients = clientService.getAllClients();
        for (Client client : clients) {
            clientService.deleteClientById(client.getClientId());
        }

    }

    @Test
    void findAllDiscounts() {
        List<Discount> discounts = discountService.findAllDiscounts();
        assertEquals(2, discounts.size());
    }

    @Test
    void findAllByDiscountType() {
        List<Discount> discounts = discountService.findAllByDiscountType(DiscountType.FIXED);
        assertEquals(1, discounts.size());
    }

    @Test
    void findByDiscoundId() {
        Discount discount = discountService.findByDiscoundId(ResultFinal.getDiscount().getDiscoundId());
        assertNotNull(discount);
    }
}