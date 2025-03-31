package com.vente.vetement.controller;

import com.vente.vetement.entity.CartItem;
import com.vente.vetement.entity.Customer;
import com.vente.vetement.entity.Product;
import com.vente.vetement.repository.CustomerRepository;
import com.vente.vetement.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ShopController {
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private CustomerRepository customerRepository;
    
    private List<CartItem> cart = new ArrayList<>();

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("cartSize", cart.size());
        model.addAttribute("title", "Accueil");
        model.addAttribute("content", "index :: content");
        return "index";
    }

    @PostMapping("/add-to-cart")
    public String addToCart(@RequestParam Long productId, @RequestParam int quantity) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product != null && product.getStock() >= quantity) {
            CartItem item = new CartItem();
            item.setProduct(product);
            item.setQuantity(quantity);
            cart.add(item);
            // Diminuer le stock
            product.setStock(product.getStock() - quantity);
            productRepository.save(product);
        }
        return "redirect:/";
    }

    @GetMapping("/cart")
    public String viewCart(Model model) {
        model.addAttribute("cartItems", cart);
        double total = cart.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
        model.addAttribute("total", total);
        model.addAttribute("customer", new Customer());
        model.addAttribute("title", "Accueil");
        model.addAttribute("content", "index :: content");
        return "cart";
    }

    @PostMapping("/remove-from-cart")
    public String removeFromCart(@RequestParam int index) {
        if (index >= 0 && index < cart.size()) {
            CartItem item = cart.get(index);
            Product product = item.getProduct();
            // Restaurer le stock
            product.setStock(product.getStock() + item.getQuantity());
            productRepository.save(product);
            cart.remove(index);
        }
        return "redirect:/cart";
    }

    @PostMapping("/checkout")
    public String checkout(@ModelAttribute Customer customer) {
        customer.setCartItems(new ArrayList<>(cart));
        customerRepository.save(customer);
        cart.clear();
        return "redirect:/";
    }
}