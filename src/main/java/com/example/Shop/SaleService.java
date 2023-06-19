package com.example.Shop;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaleService {
    private final SaleRepository saleRepository;
    private final BuyerRepository buyerRepository;
    private final ProductRepository productRepository;

    public SaleService(SaleRepository saleRepository, BuyerRepository buyerRepository, ProductRepository productRepository) {
        this.saleRepository = saleRepository;
        this.buyerRepository = buyerRepository;
        this.productRepository = productRepository;
    }

    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }

    public Sale sellProduct(Long buyerId, Long productId, int quantity) {
        Buyer buyer = buyerRepository.findById(buyerId)
                .orElseThrow(() -> new EntityNotFoundException("Buyer not found with id: " + buyerId));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + productId));

        if (quantity > product.getQuantity()) {
            throw new IllegalArgumentException("Insufficient quantity for product with id: " + productId);
        }

        Sale sale = new Sale(buyer, product, quantity);
        saleRepository.save(sale);

        buyer.getPurchases().add(sale);
        buyerRepository.save(buyer);

        int updatedQuantity = product.getQuantity() - quantity;
        product.setQuantity(updatedQuantity);
        productRepository.save(product);

        return sale;
    }
}