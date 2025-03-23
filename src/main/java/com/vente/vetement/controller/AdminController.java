package com.vente.vetement.controller;

import com.vente.vetement.entity.CartItem;
import com.vente.vetement.entity.Product;
import com.vente.vetement.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ProductRepository productRepository;
    
    private List<CartItem> cart = new ArrayList<>(); // Si partagé avec ShopController, considérez un service

    @GetMapping({"", "/dashboard"}) // Accepte /admin et /admin/dashboard
    public String adminDashboard(Model model) {
        List<Product> allProducts = productRepository.findAll();
        Map<String, Integer> categoryStats = new HashMap<>();
        for (Product product : allProducts) {
            String categoryName = product.getCategory() != null ? product.getCategory().getName() : "Sans catégorie";
            categoryStats.merge(categoryName, 1, Integer::sum);
        }

        model.addAttribute("categoryStats", categoryStats);
        model.addAttribute("cartSize", cart.size());
        return "admin/admin-dashboard";
    }
}