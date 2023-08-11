package com.restaurant.restaurant;

import com.restaurant.diningReview.DiningReviewRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/restaurants")
public class RestaurantController {
    private final RestaurantRepository restaurantRepository;
    private final DiningReviewRepository diningReviewRepository;

    public RestaurantController(
            final RestaurantRepository restaurantRepository,
            final DiningReviewRepository diningReviewRepository
    ) {
        this.restaurantRepository = restaurantRepository;
        this.diningReviewRepository = diningReviewRepository;
    }

    // Create
    @PostMapping(value = "/add")
    public ResponseEntity<Restaurant> addRestaurant(@RequestBody Restaurant restaurant) {
        boolean restaurantNameAndZipCodeExists = this.restaurantRepository.existsByDistinctRestaurantByNameAndZipCode(
                restaurant.getName(),
                restaurant.getZipCode()
        );

        if (restaurantNameAndZipCodeExists) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }

        Restaurant restaurantSaved = this.restaurantRepository.save(restaurant);
        return ResponseEntity.status(HttpStatus.OK).body(restaurantSaved);
    }

    // Read
    @GetMapping(value = "/get/{id}")
    public ResponseEntity<Restaurant> getRestaurant(@PathVariable Long id) {
        Optional<Restaurant> restaurantOptional = this.restaurantRepository.findById(id.intValue());

        if (restaurantOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Restaurant restaurant = restaurantOptional.get();
        return ResponseEntity.status(HttpStatus.OK).body(restaurant);
    }

    @GetMapping(value = "/get")
    public ResponseEntity<List<Restaurant>> getRestaurantByZipAndAllergyScore(
            @RequestParam String zipCode,
            @RequestParam String allergyType
    ) {
        switch (allergyType) {
            case "peanut": {
                List<Restaurant> restaurantsFound = this.restaurantRepository.findRestaurantByZipCodeAndPeanutAllergyScoreNotNullDesc(
                        zipCode
                );

                return ResponseEntity.status(HttpStatus.OK).body(restaurantsFound);
            }
            case "egg": {
                List<Restaurant> restaurantsFound = this.restaurantRepository.findRestaurantByZipCodeAndEggAllergyScoreNotNullDesc(
                        zipCode
                );

                return ResponseEntity.status(HttpStatus.OK).body(restaurantsFound);
            }
            case "dairy": {
                List<Restaurant> restaurantsFound = this.restaurantRepository.findRestaurantByZipCodeAndDairyAllergyScoreNotNullDesc(
                        zipCode
                );

                return ResponseEntity.status(HttpStatus.OK).body(restaurantsFound);
            }
            default: {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        }
    }
    
    /*
     Update
     NOT GOING TO BE IMPLEMENTED AT THIS STAGE
    */

    /*
     Delete
     NOT GOING TO BE IMPLEMENTED AT THIS STAGE
    */
}
