package com.ArthurBatalha.dscommerce.services;

import com.ArthurBatalha.dscommerce.dto.OrderDTO;
import com.ArthurBatalha.dscommerce.dto.OrderItemDTO;
import com.ArthurBatalha.dscommerce.entities.Order;
import com.ArthurBatalha.dscommerce.entities.OrderItem;
import com.ArthurBatalha.dscommerce.entities.OrderStatus;
import com.ArthurBatalha.dscommerce.entities.Product;
import com.ArthurBatalha.dscommerce.entities.*;
import com.ArthurBatalha.dscommerce.repositories.OrderItemRepository;
import com.ArthurBatalha.dscommerce.repositories.OrderRepository;
import com.ArthurBatalha.dscommerce.repositories.ProductRepository;
import com.ArthurBatalha.dscommerce.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private OrderItemRepository orderItemRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private AuthService authService;

    @Transactional(readOnly = true)
    public OrderDTO findById(Long id) {

        Order order = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Recurso não encontrado"));
		authService.validateSelfOrAdmin(order.getClient().getId());
        return new OrderDTO(order);
    }

	@Transactional
	public OrderDTO insert(OrderDTO dto) {
		Order order = new Order();

		order.setMoment(Instant.now());
		order.setStatus(OrderStatus.WAITING_PAYMENT);
		order.setClient(userService.authenticated());

		for (OrderItemDTO itemDTO : dto.getItems()) {
			Product product = productRepository.getReferenceById(itemDTO.getProductId());
			OrderItem item = new OrderItem(order, product,
					itemDTO.getQuantity(), product.getPrice());
			order.getItems().add(item);
		}
		repository.save(order);
		orderItemRepository.saveAll(order.getItems());

		return new OrderDTO(order);
	}
}
