package com.example.Shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class BuyerService {
    private final BuyerRepository buyerRepository;

    @Autowired
    public BuyerService(BuyerRepository buyerRepository) {
        this.buyerRepository = buyerRepository;
    }

    public List<Buyer> getAllBuyers() {
        return buyerRepository.findAll();
    }

    public Buyer getBuyerById(Long id) {
        return buyerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Buyer not found with id: " + id));
    }

    public Buyer saveBuyer(Buyer buyer) {
        return buyerRepository.save(buyer);
    }

    public void deleteBuyer(Long id) {
        if (!buyerRepository.existsById(id)) {
            throw new EntityNotFoundException("Buyer not found with id: " + id);
        }
        buyerRepository.deleteById(id);
    }
}