package com.api.dscommerce.services;

import com.api.dscommerce.dto.OrderDTO;
import com.api.dscommerce.dto.ProductDTO;
import com.api.dscommerce.entities.Order;
import com.api.dscommerce.entities.Product;
import com.api.dscommerce.repositories.OrderRepository;
import com.api.dscommerce.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Transactional(readOnly = true)
    public OrderDTO getOrderById(Long id) {
        Optional<Order> result = Optional.ofNullable(orderRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Recurso nao encotrado")
        ));
        Order order = result.get();
        OrderDTO orderDTO = new OrderDTO(order);

        return orderDTO;
    }
}
