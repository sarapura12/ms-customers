package com.client.manager.service.impl;

import com.client.manager.exception.InvalidOperationException;
import com.client.manager.model.entity.Discount;
import com.client.manager.model.enums.DiscountType;
import com.client.manager.repository.DiscountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DiscountServiceImpTest {

    @Mock
    private DiscountRepository discountRepository;

    @InjectMocks
    private DiscountServiceImp discountService;

    private Discount discount;

    @BeforeEach
    void setUp() {
        discount = new Discount();
        discount.setDiscoundId(1L);
        discount.setPercentage(10.0);
        discount.setDiscountType(DiscountType.FIXED);
    }

    @Test
    void findByDiscoundId_Success() {
        when(discountRepository.findByDiscoundId(anyLong())).thenReturn(Optional.of(discount));

        Discount foundDiscount = discountService.findByDiscoundId(1L);

        assertNotNull(foundDiscount);
        assertEquals(1L, foundDiscount.getDiscoundId());
        verify(discountRepository, times(1)).findByDiscoundId(anyLong());
    }

    @Test
    void findByDiscoundId_DiscountNotFound() {
        when(discountRepository.findByDiscoundId(anyLong())).thenReturn(Optional.empty());

        InvalidOperationException exception = assertThrows(InvalidOperationException.class, () -> {
            discountService.findByDiscoundId(1L);
        });

        assertEquals("Client not found", exception.getMessage());
        verify(discountRepository, times(1)).findByDiscoundId(anyLong());
    }

    @Test
    void findAllDiscounts_Success() {
        when(discountRepository.findAll()).thenReturn(List.of(discount));

        List<Discount> discounts = discountService.findAllDiscounts();

        assertNotNull(discounts);
        assertFalse(discounts.isEmpty());
        assertEquals(1, discounts.size());
        verify(discountRepository, times(1)).findAll();
    }
}