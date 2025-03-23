package com.vente.vetement.controller;

import com.vente.vetement.entity.Category;
import com.vente.vetement.entity.Product;
import com.vente.vetement.repository.CategoryRepository;
import com.vente.vetement.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.hibernate.Hibernate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/admin/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    public String listProducts(Model model) {
        List<Product> products = productRepository.findAll();
        for (Product product : products) {
            Hibernate.initialize(product.getCategory()); // Charge explicitement la catégorie
        }
        model.addAttribute("products", products);
        return "admin/admin-products";
    }

    @GetMapping("/add")
    public String addProductForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryRepository.findAll());
        return "admin/admin-product-form";
    }

    @PostMapping("/add")
    public String addProduct(@ModelAttribute Product product,
                             @RequestParam("imageFile") MultipartFile imageFile,
                             @RequestParam("categoryId") Long categoryId) throws IOException {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category ID"));
        product.setCategory(category);

        if (!imageFile.isEmpty()) {
            String uploadDir = "uploads/";
            Files.createDirectories(Paths.get(uploadDir));
            String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
            Path path = Paths.get(uploadDir + fileName);
            Files.write(path, imageFile.getBytes());
            product.setImage("/uploads/" + fileName);
        }

        productRepository.save(product);
        return "redirect:/admin/products";
    }

    @GetMapping("/edit/{id}")
    public String editProductForm(@PathVariable Long id, Model model) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product ID"));
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryRepository.findAll());
        return "admin/admin-product-form";
    }

    @PostMapping("/edit/{id}")
    public String editProduct(@PathVariable Long id,
                              @ModelAttribute Product product,
                              @RequestParam("imageFile") MultipartFile imageFile,
                              @RequestParam("categoryId") Long categoryId) throws IOException {
        product.setId(id);
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category ID"));
        product.setCategory(category);

        if (!imageFile.isEmpty()) {
            String uploadDir = "uploads/";
            Files.createDirectories(Paths.get(uploadDir));
            String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
            Path path = Paths.get(uploadDir + fileName);
            Files.write(path, imageFile.getBytes());
            product.setImage("/uploads/" + fileName);
        } else {
            Product existingProduct = productRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid product ID"));
            product.setImage(existingProduct.getImage());
        }

        productRepository.save(product);
        return "redirect:/admin/products";
    }

    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);
        return "redirect:/admin/products";
    }
}