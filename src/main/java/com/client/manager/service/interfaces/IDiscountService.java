package com.client.manager.service.interfaces;

import com.client.manager.model.entity.Discount;
import com.client.manager.model.enums.DiscountType;

import java.util.List;
import java.util.Optional;

public interface IDiscountService {

    List<Discount> findAllDiscounts();
    List<Discount> findAllByDiscountType(DiscountType discountType);
    Discount findByDiscoundId(Long discountId);

}
