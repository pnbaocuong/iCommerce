package com.pnbaocuong.productservice.service;

import com.pnbaocuong.productservice.entity.Product;
import com.pnbaocuong.productservice.entity.ProductPriceHistory;
import com.pnbaocuong.productservice.repository.ProductPriceHistoryRepository;
import com.pnbaocuong.productservice.repository.ProductRepository;
import com.querydsl.core.types.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductPriceHistoryRepository priceRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductPriceHistoryRepository priceRepository) {
        this.productRepository = productRepository;
        this.priceRepository = priceRepository;
    }

    public List<Product> findAll(Predicate predicate, Pageable pageable) {
        return productRepository.findAll(predicate, pageable).getContent();
    }

    public Product findProductById(String id) {
        return productRepository.findById(Long.parseLong(id)).orElseThrow();
    }

    public Product add(Product product) {
        return productRepository.save(product);
    }

    public Product update(Product newProduct) {
        trackPriceChange(newProduct);
        return productRepository.save(newProduct);
    }

    private void trackPriceChange(Product newProduct) {
        Product oldProduct = productRepository.findById(newProduct.getId()).orElseThrow();
        if (newProduct.getPrice().compareTo(oldProduct.getPrice()) != 0) {
            ProductPriceHistory productPriceHistory = new ProductPriceHistory(oldProduct, newProduct.getPrice());
            priceRepository.save(productPriceHistory);
        }
    }

}
