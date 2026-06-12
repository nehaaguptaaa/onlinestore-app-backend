package com.app.onlinestore.service;

import com.app.onlinestore.model.Cart;
import com.app.onlinestore.model.Order;
import com.app.onlinestore.model.User;
import com.app.onlinestore.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OrderService {

    @Autowired private OrderRepository orderRepository;
    @Autowired private CartService cartService;
    @Autowired private UserService userService;





   @Transactional
    public String placeOrder(String email) {
        List<Cart> cartItems = cartService
                .getCartByUser(email);
        if (cartItems.isEmpty()) {
            return "Cart is empty!";
        }

        for (Cart item : cartItems) {
            Order order = new Order();
            order.setUserId(userService
                    .getUserByEmail(email).getId());
            order.setProductId(item.getProductId());
            order.setProductName(item.getProductName());
            order.setQuantity(item.getQuantity());
            order.setTotalPrice(
                    item.getPrice() * item.getQuantity());
            orderRepository.save(order);
        }

        cartService.clearCart(email);
        return "Order placed successfully!";
    }

    public List<Order> getOrderHistory(String email) {
        User user = userService.getUserByEmail(email);
        return orderRepository.findByUserId(user.getId());
    }
}