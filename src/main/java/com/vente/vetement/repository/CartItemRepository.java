package com.vente.vetement.repository;

import com.vente.vetement.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    @Query("SELECT MONTH(ci.purchaseDate) as month, COUNT(ci) as count FROM CartItem ci GROUP BY MONTH(ci.purchaseDate)")
    List<Object[]> countCartItemsByMonth();
}