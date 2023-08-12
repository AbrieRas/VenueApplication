package com.restaurant.restaurant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantDTO {
    private Long id;
    private String name;
    private String zipCode;
    private Float peanutAllergyScore;
    private Float eggAllergyScore;
    private Float dairyAllergyScore;
}
