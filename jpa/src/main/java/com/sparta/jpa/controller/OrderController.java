package com.sparta.jpa.controller;

import com.sparta.jpa.dto.OrderRequestDto;
import com.sparta.jpa.dto.OrderResponseDto;
import com.sparta.jpa.service.OrderService;
import com.sparta.jpa.validator.OrderValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController

public class OrderController {
    private final OrderService orderService;

    @PostMapping("/order/request")
    public OrderResponseDto createOrder(@RequestBody OrderRequestDto requestDto){
        OrderValidator.validateOrderInput(requestDto);
        OrderResponseDto orderResponse = orderService.createOrders(requestDto);
        return orderResponse;
    }

    @GetMapping("/orders")
    public List<OrderResponseDto> getOrders(){
        List<OrderResponseDto> orderLists = orderService.getOrders();
        return orderLists;
    }
}
