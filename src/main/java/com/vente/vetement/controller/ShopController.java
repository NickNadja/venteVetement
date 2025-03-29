package com.vente.vetement.controller;

import com.vente.vetement.entity.CartItem;
import com.vente.vetement.entity.Category;
import com.vente.vetement.entity.Customer;
import com.vente.vetement.entity.Product;
import com.vente.vetement.repository.CategoryRepository;
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

    @Autowired
    private CategoryRepository categoryRepository;
    
    private List<CartItem> cart = new ArrayList<>();

    @GetMapping("/")
    public String home(Model model, 
                       @RequestParam(value = "search", required = false) String search,
                       @RequestParam(value = "category", required = false) String categoryName) {
        List<Product> products;
        String searchTerm = (search != null && !search.trim().isEmpty()) ? search.trim() : "";
        Category category = (categoryName != null && !categoryName.trim().isEmpty()) ? categoryRepository.findByName(categoryName.trim()) : null;

        products = productRepository.findByProductNameContainingAndCategory(searchTerm, category);
        
        model.addAttribute("products", products);
        model.addAttribute("cartSize", cart.size());
        model.addAttribute("search", searchTerm);
        model.addAttribute("category", categoryName);
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
        return "cart";
    }

    @PostMapping("/remove-from-cart")
    public String removeFromCart(@RequestParam int index) {
        if (index >= 0 && index < cart.size()) {
            CartItem item = cart.get(index);
            Product product = item.getProduct();
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

    @GetMapping("/tees")
    public String showAdminTeeShirts(Model model) {
        Category teeShirtCategory = categoryRepository.findByName("T-shirt");
        List<Product> teeShirts = productRepository.findByProductNameContainingAndCategory("", teeShirtCategory);
        
        model.addAttribute("products", teeShirts);
        model.addAttribute("cartSize", cart.size());
        model.addAttribute("category", "T-shirt");
        return "tees";
    }
    @GetMapping("/pantalon")
    public String showAdminPantalon(Model model) {
        Category pantalonCategory = categoryRepository.findByName("Pantalon");
        List<Product> pantalon = productRepository.findByProductNameContainingAndCategory("", pantalonCategory);
        
        model.addAttribute("products", pantalon);
        model.addAttribute("cartSize", cart.size());
        model.addAttribute("category", "Pantalon");
        return "tees";
    }
    @GetMapping("/shorts")
    public String showAdminShorts(Model model) {
        Category shortsCategory = categoryRepository.findByName("Shorts");
        List<Product> shorts = productRepository.findByProductNameContainingAndCategory("", shortsCategory);
        
        model.addAttribute("products", shorts);
        model.addAttribute("cartSize", cart.size());
        model.addAttribute("category", "Shorts");
        return "tees";
    }
    @GetMapping("/chaussures")
    public String showAdminChaussures(Model model) {
        Category chaussuresCategory = categoryRepository.findByName("Chaussures");
        List<Product> chaussures = productRepository.findByProductNameContainingAndCategory("", chaussuresCategory);
        
        model.addAttribute("products", chaussures);
        model.addAttribute("cartSize", cart.size());
        model.addAttribute("category", "Chaussures");
        return "tees";
    }
    @GetMapping("/manteaux")
    public String showAdminManteaux(Model model) {
        Category manteauxCategory = categoryRepository.findByName("Manteaux");
        List<Product> manteaux = productRepository.findByProductNameContainingAndCategory("", manteauxCategory);
        
        model.addAttribute("products", manteaux);
        model.addAttribute("cartSize", cart.size());
        model.addAttribute("category", "Manteaux");
        return "tees";
    }


    @GetMapping("/resultats")
    public String resRech(Model model, 
                       @RequestParam(value = "search", required = false) String search,
                       @RequestParam(value = "category", required = false) String categoryName) {
        List<Product> products;
        String searchTerm = (search != null && !search.trim().isEmpty()) ? search.trim() : "";

        if (!searchTerm.isEmpty()) {
            // Recherche multi-champs
            products = productRepository.searchProducts(searchTerm);
        } else if (categoryName != null && !categoryName.trim().isEmpty()) {
            // Filtrer par catégorie si pas de recherche
            Category category = categoryRepository.findByName(categoryName.trim());
            products = productRepository.findByProductNameContainingAndCategory("", category);
        } else {
            // Afficher tous les produits si aucun critère
            products = productRepository.findAll();
        }
        
        model.addAttribute("products", products);
        model.addAttribute("cartSize", cart.size());
        model.addAttribute("search", searchTerm);
        model.addAttribute("category", categoryName);
        return "tees";
    }
}