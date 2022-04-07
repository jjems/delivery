package com.sparta.jpa.repository;
import com.sparta.jpa.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders, Long> {
}


