package com.client.manager.repository;

import com.client.manager.model.entity.Discount;
import com.client.manager.model.enums.DiscountType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DiscountRepository extends JpaRepository<Discount, Long> {


    Optional<Discount> findAllByDiscountType(DiscountType discountType);
    Optional<Discount> findByDiscoundId(Long discountId);
}
