package com.app.onlinestore.controller;

import com.app.onlinestore.dto.CartRequestDTO;
import com.app.onlinestore.model.Cart;
import com.app.onlinestore.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired private CartService cartService;

    @PostMapping("/add")
    public Cart addToCart(
            @RequestBody CartRequestDTO dto,
            Authentication auth) {
        return cartService.addToCart(dto, auth.getName());
    }

    @GetMapping
    public List<Cart> getCart(Authentication auth) {
        return cartService.getCartByUser(auth.getName());
    }

    @DeleteMapping("/{id}")
    public String removeFromCart(@PathVariable Long id) {
        cartService.removeFromCart(id);
        return "Item removed from cart!";
    }
}