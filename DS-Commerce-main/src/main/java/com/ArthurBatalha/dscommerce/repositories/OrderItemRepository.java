package com.ArthurBatalha.dscommerce.repositories;

import com.ArthurBatalha.dscommerce.entities.OrderItem;
import com.ArthurBatalha.dscommerce.entities.OrderItemPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem,
		OrderItemPK> {

}
