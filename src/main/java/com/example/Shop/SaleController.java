package com.example.Shop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;

@Controller
@RequestMapping("/sales")
public class SaleController {

    private final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @PostMapping
    public String createSale(@RequestParam(value = "buyerId") Long buyerId,
                             @RequestParam(value = "productId") Long productId,
                             @RequestParam(value = "quantity") int quantity,
                             RedirectAttributes redirectAttributes) {
        try {
            Sale sale = saleService.sellProduct(buyerId, productId, quantity);
            redirectAttributes.addFlashAttribute("successMessage", "Sale created successfully.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to create sale: " + e.getMessage());
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to create sale: " + e.getMessage());
        }
        return "redirect:/sales";
    }

    @GetMapping
    public String getSales(Model model) {
        List<Sale> sales = saleService.getAllSales();
        model.addAttribute("sales", sales);
        return "sales";
    }
}