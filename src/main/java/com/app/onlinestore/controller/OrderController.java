package com.app.onlinestore.controller;

import com.app.onlinestore.model.Order;
import com.app.onlinestore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired private OrderService orderService;

    @PostMapping("/place")
    public String placeOrder(Authentication auth) {
        return orderService.placeOrder(auth.getName());
    }

    @GetMapping("/history")
    public List<Order> getOrderHistory(
            Authentication auth) {
        return orderService.getOrderHistory(auth.getName());
    }
}