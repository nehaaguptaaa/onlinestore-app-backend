package com.app.onlinestore.repository;

import com.app.onlinestore.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository
        extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
}