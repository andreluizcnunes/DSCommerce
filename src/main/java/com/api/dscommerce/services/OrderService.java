package com.api.dscommerce.services;

import com.api.dscommerce.dto.OrderDTO;
import com.api.dscommerce.dto.OrderItemDTO;
import com.api.dscommerce.entities.Order;
import com.api.dscommerce.entities.OrderItem;
import com.api.dscommerce.entities.Product;
import com.api.dscommerce.entities.User;
import com.api.dscommerce.entities.enuns.OrderStatus;
import com.api.dscommerce.repositories.OrderItemRepository;
import com.api.dscommerce.repositories.OrderRepository;
import com.api.dscommerce.repositories.ProductRepository;
import com.api.dscommerce.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private UserService userService;

    @Transactional(readOnly = true)
    public OrderDTO getOrderById(Long id) {
        Optional<Order> result = Optional.ofNullable(orderRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Recurso nao encotrado")
        ));
        Order order = result.get();
        OrderDTO orderDTO = new OrderDTO(order);

        return orderDTO;
    }

    @Transactional
    public OrderDTO createOrder(OrderDTO orderDTO) {

        Order order = new Order();
        order.setMoment(Instant.now());
        order.setStatus(OrderStatus.WAITING_PAYMENT);

        User user = userService.authenticate();
        order.setClient(user);

        for(OrderItemDTO itemDTO : orderDTO.getItens()){
            Product product = productRepository.getReferenceById(itemDTO.getProductId());
            OrderItem item = new OrderItem(order, product, itemDTO.getQuantity(), product.getPrice());
            order.getItems().add(item);
        }

        orderRepository.save(order);
        orderItemRepository.saveAll(order.getItems());

        return new OrderDTO(order);
    }
;}
