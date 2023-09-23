package com.example.LaundrySystem.Repositories;

import com.example.LaundrySystem.Entities.NotePrimaryKey;
import com.example.LaundrySystem.Entities.Order;
import com.example.LaundrySystem.Entities.OrderNote;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderNoteRepository extends JpaRepository<OrderNote, NotePrimaryKey> {
    @Transactional
    void deleteByOrder(Order order);
}
