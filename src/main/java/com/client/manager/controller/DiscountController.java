package com.client.manager.controller;

import com.client.manager.dto.DiscountDto;
import com.client.manager.model.entity.Discount;
import com.client.manager.model.enums.DiscountType;
import com.client.manager.service.interfaces.IDiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/discounts")
public class DiscountController {

    @Autowired
    private IDiscountService discountService;

    @GetMapping("/searchAll")
    public ResponseEntity<List<Discount>> getAllDiscounts() {
        List<Discount> discounts = discountService.findAllDiscounts();
        return ResponseEntity.ok(discounts);
    }

    @GetMapping("/search/byType")
    public ResponseEntity<List<Discount>> getDiscountsByType(@RequestParam DiscountType discountType) {
        List<Discount> discounts = discountService.findAllByDiscountType(discountType);
        return ResponseEntity.ok(discounts);
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<Discount> getDiscountById(@PathVariable Long id) {
        Discount discount = discountService.findByDiscoundId(id);
        return ResponseEntity.ok(discount);
    }
}