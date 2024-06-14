package com.api.dscommerce.dto;

import com.api.dscommerce.entities.Order;
import com.api.dscommerce.entities.OrderItem;
import com.api.dscommerce.entities.enuns.OrderStatus;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class OrderDTO {

    private Long id;
    private Instant moment;
    private OrderStatus status;

    private ClientDTO client;
    private PaymentDTO payment;

    private List<OrderItemDTO> itens = new ArrayList<>();

    public OrderDTO(Long id, Instant moment, OrderStatus status, ClientDTO client, PaymentDTO payment) {
        this.id = id;
        this.moment = moment;
        this.status = status;
        this.client = client;
        this.payment = payment;
    }

    public OrderDTO(Order entity) {
        id = entity.getId();
        moment = entity.getMoment();
        status = entity.getStatus();
        client = new ClientDTO(entity.getClient());
        payment = (entity.getPayment() != null) ? new PaymentDTO(entity.getPayment()) : null;

        for(OrderItem item : entity.getItems()){
            itens.add(new OrderItemDTO(item));
        }
    }

    public Long getId() {
        return id;
    }

    public Instant getMoment() {
        return moment;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public ClientDTO getClient() {
        return client;
    }

    public PaymentDTO getPayment() {
        return payment;
    }

    public List<OrderItemDTO> getItens() {
        return itens;
    }

    public Double getTotal() {
        double total = 0;
        for (OrderItemDTO item : itens) {
            total += item.getSubTotal();
        }

        return total;
    }
}
