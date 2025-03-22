package com.vente.vetement.controller;

import com.vente.vetement.entity.Customer;
import com.vente.vetement.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/purchases")
public class PurchaseController {

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping
    public String listPurchases(Model model) {
        List<Customer> customers = customerRepository.findAll();
        Map<Long, Double> purchaseTotals = new HashMap<>();
        for (Customer customer : customers) {
            double total = customer.getCartItems().stream()
                    .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                    .sum();
            purchaseTotals.put(customer.getId(), total);
        }
        model.addAttribute("customers", customers);
        model.addAttribute("purchaseTotals", purchaseTotals);
        return "admin/admin-purchases";
    }

    @PostMapping("/delete/{id}")
    public String deletePurchases(@PathVariable Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid customer ID"));
        customer.getCartItems().clear();
        customerRepository.save(customer);
        return "redirect:/admin/purchases";
    }
}