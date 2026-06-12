package com.app.onlinestore.service;

import com.app.onlinestore.dto.CartRequestDTO;
import com.app.onlinestore.model.Cart;
import com.app.onlinestore.model.Product;
import com.app.onlinestore.model.User;
import com.app.onlinestore.repository.CartRepository;
import com.app.onlinestore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CartService {

    @Autowired private CartRepository cartRepository;
    @Autowired private ProductRepository productRepository;
    @Autowired private UserService userService;

    public Cart addToCart(CartRequestDTO dto, String email) {
        User user = userService.getUserByEmail(email);
        Product product = productRepository
                .findById(dto.getProductId()).orElse(null);
        if (product == null) return null;

        Cart cart = new Cart();
        cart.setUserId(user.getId());
        cart.setProductId(product.getId());
        cart.setProductName(product.getName());
        cart.setQuantity(dto.getQuantity());
        cart.setPrice(product.getOnSale() ?
                product.getSalePrice() : product.getPrice());
        return cartRepository.save(cart);
    }

    public List<Cart> getCartByUser(String email) {
        User user = userService.getUserByEmail(email);
        return cartRepository.findByUserId(user.getId());
    }

    public void removeFromCart(Long cartId) {
        cartRepository.deleteById(cartId);
    }

    public void clearCart(String email) {
        User user = userService.getUserByEmail(email);
        cartRepository.deleteByUserId(user.getId());
    }
}