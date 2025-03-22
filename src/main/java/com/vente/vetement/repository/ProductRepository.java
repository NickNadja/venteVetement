package com.vente.vetement.repository;

import com.vente.vetement.entity.Category;
import com.vente.vetement.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE LOWER(p.productName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) AND (:category IS NULL OR p.category = :category)")
    List<Product> findByProductNameContainingAndCategory(@Param("searchTerm") String searchTerm, @Param("category") Category category);
}