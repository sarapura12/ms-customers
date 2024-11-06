package com.client.manager.service.impl;


import com.client.manager.exception.InvalidOperationException;
import com.client.manager.model.entity.Discount;
import com.client.manager.model.enums.DiscountType;
import com.client.manager.repository.DiscountRepository;
import com.client.manager.service.interfaces.IDiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscountServiceImp implements IDiscountService {

    @Autowired
    private DiscountRepository discountRepository;

    @Override
    public Discount findByDiscoundId(Long discountId) {

        return discountRepository.findByDiscoundId(discountId)
                .map(List::of)
                .orElseThrow(() -> new InvalidOperationException("Client", discountId, "Client not found"))
                .get(0);
    }

    @Override
    public List<Discount> findAllByDiscountType(DiscountType discountType) {
        return discountRepository.findAllByDiscountType(discountType)
                .stream().toList();
    }

    @Override
    public List<Discount> findAllDiscounts() {
        return discountRepository.findAll();
    }
}
