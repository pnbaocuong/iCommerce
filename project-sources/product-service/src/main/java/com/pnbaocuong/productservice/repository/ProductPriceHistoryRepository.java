package com.pnbaocuong.productservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pnbaocuong.productservice.entity.ProductPriceHistory;

import java.util.List;

public interface ProductPriceHistoryRepository extends JpaRepository<ProductPriceHistory, Long> {
    List<ProductPriceHistory> findAllByProductId(Long productId);
}
