package com.example.Shop;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/buyers")
@CrossOrigin(origins = {"http://localhost:4200", "http://127.0.0.1:4200"})
public class BuyerController {
    private final BuyerService buyerService;

    public BuyerController(BuyerService buyerService) {
        this.buyerService = buyerService;
    }

    @GetMapping
    public String getAllBuyers(Model model) {
        List<Buyer> buyers = buyerService.getAllBuyers();
        model.addAttribute("buyers", buyers);
        return "buyers";
    }

    @GetMapping("/{id}")
    public ResponseEntity<Buyer> getBuyerById(@PathVariable Long id) {
        Buyer buyer = buyerService.getBuyerById(id);
        if (buyer != null) {
            return ResponseEntity.ok(buyer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Buyer> addBuyer(@RequestBody Buyer buyer) {
        Buyer newBuyer = buyerService.saveBuyer(buyer);
        return ResponseEntity.status(HttpStatus.CREATED).body(newBuyer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Buyer> updateBuyer(@PathVariable Long id, @RequestBody Buyer updatedBuyer) {
        Buyer existingBuyer = buyerService.getBuyerById(id);
        if (existingBuyer != null) {
            existingBuyer.setName(updatedBuyer.getName());
            existingBuyer.setEmail(updatedBuyer.getEmail());
            existingBuyer.setAddress(updatedBuyer.getAddress());
            updatedBuyer = buyerService.saveBuyer(existingBuyer);
            return ResponseEntity.ok(updatedBuyer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBuyer(@PathVariable Long id) {
        buyerService.deleteBuyer(id);
        return ResponseEntity.noContent().build();
    }
}