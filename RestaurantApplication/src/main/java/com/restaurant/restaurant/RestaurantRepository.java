package com.restaurant.restaurant;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RestaurantRepository extends CrudRepository<Restaurant, Integer> {
    Boolean existsByDistinctRestaurantByNameAndZipCode(String name, String zipCode);
    List<Restaurant> findRestaurantByZipCodeAndPeanutAllergyScoreNotNullDesc(String zipCode);
    List<Restaurant> findRestaurantByZipCodeAndEggAllergyScoreNotNullDesc(String zipCode);
    List<Restaurant> findRestaurantByZipCodeAndDairyAllergyScoreNotNullDesc(String zipCode);
}
