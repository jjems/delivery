package com.sparta.jpa.controller;


import com.sparta.jpa.dto.FoodRequestDto;
import com.sparta.jpa.model.Food;
import com.sparta.jpa.service.FoodService;
import com.sparta.jpa.validator.FoodValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController

public class FoodController {
    private final FoodService foodservice;

    @GetMapping("/restaurant/{restaurantId}/foods")
    public List<Food> getFoods(@PathVariable Long restaurantId){
        List<Food> foods = foodservice.getFoods(restaurantId);
        return foods;
    }

    @PostMapping("restaurant/{restaurantId}/food/register")
    public void createFoods(@RequestBody List<FoodRequestDto> requestDtoList, @PathVariable Long restaurantId){
        FoodValidator.validateFoodInput(requestDtoList);
        foodservice.createFoods(requestDtoList, restaurantId);
    }
}
