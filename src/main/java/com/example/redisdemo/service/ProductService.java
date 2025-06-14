package com.example.redisdemo.service;

import com.example.redisdemo.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class ProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);
    
    // Simulating a database with a Map
    private final Map<String, Product> productDatabase = new HashMap<>();

    // Initialize with some sample data
    public ProductService() {
        // Add some sample products
        Product product1 = new Product();
        product1.setId("P001");
        product1.setName("iPhone 15 Pro");
        product1.setDescription("Latest Apple iPhone with advanced features");
        product1.setPrice(new BigDecimal("999.99"));
        product1.setStockQuantity(100);
        product1.setCategory("Electronics");
        product1.setLastUpdated(System.currentTimeMillis());
        productDatabase.put(product1.getId(), product1);

        Product product2 = new Product();
        product2.setId("P002");
        product2.setName("Samsung 4K TV");
        product2.setDescription("55-inch 4K Smart TV");
        product2.setPrice(new BigDecimal("799.99"));
        product2.setStockQuantity(50);
        product2.setCategory("Electronics");
        product2.setLastUpdated(System.currentTimeMillis());
        productDatabase.put(product2.getId(), product2);
    }

    @Cacheable(value = "products", key = "#id", unless = "#result == null")
    public Product getProduct(String id) {
        logger.info("Cache miss for product: {}", id);
        // Simulate database delay
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return productDatabase.get(id);
    }

    @CacheEvict(value = "products", key = "#product.id")
    public Product updateProduct(Product product) {
        logger.info("Evicting cache for product: {}", product.getId());
        product.setLastUpdated(System.currentTimeMillis());
        productDatabase.put(product.getId(), product);
        return product;
    }

    public Product createProduct(Product product) {
        product.setId("P" + UUID.randomUUID().toString().substring(0, 8));
        product.setLastUpdated(System.currentTimeMillis());
        productDatabase.put(product.getId(), product);
        return product;
    }

    @CacheEvict(value = "products", key = "#id")
    public void deleteProduct(String id) {
        logger.info("Evicting cache for product: {}", id);
        productDatabase.remove(id);
    }
}