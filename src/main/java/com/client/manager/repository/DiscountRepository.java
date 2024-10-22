package com.client.manager.repository;

import com.client.manager.model.entity.Discount;
import com.client.manager.model.enums.DiscountType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiscountRepository extends JpaRepository<Discount, Long> {


    List<Discount> getAll();
    List<Discount> findAllByDiscountType(DiscountType discountType);
    Discount findByDiscountId(Long discountId);
}
