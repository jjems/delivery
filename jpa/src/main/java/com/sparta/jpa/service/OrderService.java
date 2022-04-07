package com.sparta.jpa.service;

import com.sparta.jpa.dto.OrderItemRequestDto;
import com.sparta.jpa.dto.OrderRequestDto;
import com.sparta.jpa.dto.OrderResponseDto;
import com.sparta.jpa.dto.OrderResponseFoodsDto;
import com.sparta.jpa.model.Food;
import com.sparta.jpa.model.OrderItem;
import com.sparta.jpa.model.Orders;
import com.sparta.jpa.model.Restaurant;
import com.sparta.jpa.repository.FoodRepository;
import com.sparta.jpa.repository.OrderItemRepository;
import com.sparta.jpa.repository.OrderRepository;
import com.sparta.jpa.repository.RestaurantRepository;
import com.sparta.jpa.validator.OrderValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor

public class OrderService{
    private RestaurantRepository restaurantRepository;
    private FoodRepository foodRepository;
    private OrderRepository orderRepository;
    private OrderItemRepository orderItemRepository;

    @Autowired
    public OrderService(RestaurantRepository restaurantRepository, FoodRepository foodRepository, OrderRepository orderRepository, OrderItemRepository orderItemRepository){
        this.foodRepository = foodRepository;
        this.restaurantRepository = restaurantRepository;
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
    };

    @Transactional
    public OrderResponseDto createOrders(OrderRequestDto requestDto) {
        //레스토랑 ID로 주문 음식점 불러오기 id, name, minOrderPrice, deliveryFee

        Restaurant restaurant = restaurantRepository.findById(requestDto.getRestaurantId())
                .orElse(null);

        String restaurantName = restaurant.getName();
        int minOrderPrice = restaurant.getMinOrderPrice();
        int deliveryFee = restaurant.getDeliveryFee();

        Orders orders = new Orders(restaurant);
        orderRepository.save(orders);

        List<OrderResponseFoodsDto> foods = new ArrayList<>();
        int totalPrice = 0;
        for(OrderItemRequestDto eachItem : requestDto.getFoods()){
            Food food = foodRepository.findById(eachItem.getId())
                    .orElse(null);

            int quantity = eachItem.getQuantity();
            int price = food.getPrice() * quantity;

            OrderItem orderItem = new OrderItem(orders, food, quantity, price);
            orderItemRepository.save(orderItem);

            OrderResponseFoodsDto eachFoodResponse = new OrderResponseFoodsDto(food.getName(), quantity, price);
            foods.add(eachFoodResponse);
            totalPrice += price;
        }

        OrderValidator.validateOrderPrice(minOrderPrice, totalPrice);

        totalPrice = totalPrice + deliveryFee;

        OrderResponseDto orderResponse = new OrderResponseDto(restaurantName, foods, deliveryFee, totalPrice);

        return orderResponse;

    }

    public List<OrderResponseDto> getOrders() {
        List<Orders> orders = orderRepository.findAll();
        List<OrderResponseDto> orderLists = new ArrayList<>();

        for(Orders eachOrder : orders){
            String restaurantName = eachOrder.getRestaurant().getName();
            int deliveryFee = eachOrder.getRestaurant().getDeliveryFee();

            List<OrderResponseFoodsDto> foods = new ArrayList<>();
            List<OrderItem> foodsTemp = orderItemRepository.findAllByOrdersId(eachOrder.getId());
            int totalPrice = 0;
            for(OrderItem eachItem : foodsTemp){
                String name = eachItem.getFood().getName();
                int quantity = eachItem.getQuantity();
                int price = eachItem.getPrice();

                OrderResponseFoodsDto eachItems = new OrderResponseFoodsDto(name, quantity, price);
                foods.add(eachItems);
                totalPrice += price;
            }
            totalPrice = totalPrice + deliveryFee;
            OrderResponseDto orderResponseDto = new OrderResponseDto(restaurantName, foods, deliveryFee, totalPrice);
            orderLists.add(orderResponseDto);
        }
        return orderLists;
    }
}
