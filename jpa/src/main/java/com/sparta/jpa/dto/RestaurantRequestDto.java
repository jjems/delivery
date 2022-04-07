package com.sparta.jpa.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor

public class RestaurantRequestDto {
    private final String name;
    private final int minOrderPrice;
    private final int deliveryFee;

}
