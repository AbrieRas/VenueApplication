package com.restaurant.restaurant;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {
    Boolean existsByNameAndZipCode(String name, String zipCode);
    List<Restaurant> findByZipCodeAndPeanutAllergyScoreNotNullOrderByNameDesc(String zipCode);
    List<Restaurant> findByZipCodeAndEggAllergyScoreNotNullOrderByNameDesc(String zipCode);
    List<Restaurant> findByZipCodeAndDairyAllergyScoreNotNullOrderByNameDesc(String zipCode);
}
