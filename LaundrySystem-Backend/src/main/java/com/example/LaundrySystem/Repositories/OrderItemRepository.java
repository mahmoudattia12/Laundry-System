package com.example.LaundrySystem.Repositories;

import com.example.LaundrySystem.Entities.ItemPrimaryKey;
import com.example.LaundrySystem.Entities.Order;
import com.example.LaundrySystem.Entities.OrderItem;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, ItemPrimaryKey> {
    @Transactional
    void deleteByOrder(Order order);
}
