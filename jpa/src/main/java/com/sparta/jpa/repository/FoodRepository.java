package com.sparta.jpa.repository;

import com.sparta.jpa.model.Food;
import com.sparta.jpa.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Long> {
    List<Food> findAllByRestaurant(Restaurant restaurant);
//    List<FoodMapping> findAllByRestaurant(Restaurant restaurant);


//    @Query("SELECT id, name, price FROM Food")
//    List<Object> findAllByRestaurant(Restaurant restaurant);
}
