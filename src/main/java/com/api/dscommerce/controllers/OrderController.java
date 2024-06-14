package com.api.dscommerce.controllers;

import com.api.dscommerce.dto.OrderDTO;
import com.api.dscommerce.dto.ProductDTO;
import com.api.dscommerce.dto.ProductMinDTO;
import com.api.dscommerce.services.OrderService;
import com.api.dscommerce.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
    public ResponseEntity<OrderDTO> getProductById(@PathVariable Long id) {
        OrderDTO dto = orderService.getOrderById(id);
        return ResponseEntity.ok(dto);
    }

    @PreAuthorize("hasAnyRole('ROLE_CLIENT')")
    @PostMapping
    public ResponseEntity<OrderDTO> createProduct(@Valid @RequestBody OrderDTO orderDTO) {
        orderDTO = orderService.createOrder(orderDTO);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(orderDTO.getId())
                .toUri();
        return ResponseEntity.created(uri).body(orderDTO);
    }
}
