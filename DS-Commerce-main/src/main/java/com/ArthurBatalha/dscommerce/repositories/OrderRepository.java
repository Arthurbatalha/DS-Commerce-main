package com.ArthurBatalha.dscommerce.repositories;

import com.ArthurBatalha.dscommerce.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
