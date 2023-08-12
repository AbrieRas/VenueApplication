package com.restaurant.restaurant;

import com.restaurant.diningReview.DiningReviewRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public ResponseEntity<RestaurantDTO> addRestaurant(@RequestBody Restaurant restaurant) {
        boolean restaurantNameAndZipCodeExists = this.restaurantRepository.existsByNameAndZipCode(
                restaurant.getName(),
                restaurant.getZipCode()
        );

        if (restaurantNameAndZipCodeExists) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }

        Restaurant restaurantSaved = this.restaurantRepository.save(restaurant);
        RestaurantDTO restaurantDTO = this.createRestaurantDTO(restaurantSaved);
        return ResponseEntity.status(HttpStatus.OK).body(restaurantDTO);
    }

    // Read
    @GetMapping(value = "/get/{id}")
    public ResponseEntity<RestaurantDTO> getRestaurant(@PathVariable Long id) {
        Optional<Restaurant> restaurantOptional = this.restaurantRepository.findById(id.intValue());

        if (restaurantOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Restaurant restaurant = restaurantOptional.get();
        RestaurantDTO restaurantDTO = this.createRestaurantDTO(restaurant);
        return ResponseEntity.status(HttpStatus.OK).body(restaurantDTO);
    }

    @GetMapping(value = "/get")
    public ResponseEntity<List<RestaurantDTO>> getRestaurantByZipAndAllergyScore(
            @RequestParam String zipCode,
            @RequestParam String allergyType
    ) {
        if (zipCode == null || allergyType == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        List<Restaurant> restaurantsFound;

        switch (allergyType) {
            case "peanut": {
                restaurantsFound = this.restaurantRepository.findByZipCodeAndPeanutAllergyScoreNotNullOrderByNameDesc(
                        zipCode
                );
                break;
            }
            case "egg": {
                 restaurantsFound = this.restaurantRepository.findByZipCodeAndEggAllergyScoreNotNullOrderByNameDesc(
                        zipCode
                );
                break;
            }
            case "dairy": {
                restaurantsFound = this.restaurantRepository.findByZipCodeAndDairyAllergyScoreNotNullOrderByNameDesc(
                        zipCode
                );
                break;
            }
            default: {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        }

        List<RestaurantDTO> restaurantDTOs = this.createRestaurantDTO(restaurantsFound);
        return ResponseEntity.status(HttpStatus.OK).body(restaurantDTOs);
    }
    
    /*
     Update
     NOT GOING TO BE IMPLEMENTED AT THIS STAGE
    */

    /*
     Delete
     NOT GOING TO BE IMPLEMENTED AT THIS STAGE
    */

    private RestaurantDTO createRestaurantDTO(Restaurant restaurant) {
        RestaurantDTO restaurantDTO = new RestaurantDTO();

        restaurantDTO.setId(restaurant.getId());

        if (restaurant.getName() != null) {
            restaurantDTO.setName(restaurant.getName());
        }
        if (restaurant.getZipCode() != null) {
            restaurantDTO.setZipCode(restaurant.getZipCode());
        }
        if (restaurant.getPeanutAllergyScore() != null) {
            restaurantDTO.setPeanutAllergyScore(restaurant.getPeanutAllergyScore());
        }
        if (restaurant.getEggAllergyScore() != null) {
            restaurantDTO.setEggAllergyScore(restaurant.getEggAllergyScore());
        }
        if (restaurant.getDairyAllergyScore() != null) {
            restaurantDTO.setDairyAllergyScore(restaurant.getDairyAllergyScore());
        }

        return restaurantDTO;
    }

    private List<RestaurantDTO> createRestaurantDTO(List<Restaurant> restaurants) {
        List<RestaurantDTO> restaurantDTOs = new ArrayList<>();

        for (Restaurant restaurant : restaurants) {
            RestaurantDTO restaurantDTO = new RestaurantDTO();

            restaurantDTO.setId(restaurant.getId());

            if (restaurant.getName() != null) {
                restaurantDTO.setName(restaurant.getName());
            }
            if (restaurant.getZipCode() != null) {
                restaurantDTO.setZipCode(restaurant.getZipCode());
            }
            if (restaurant.getPeanutAllergyScore() != null) {
                restaurantDTO.setPeanutAllergyScore(restaurant.getPeanutAllergyScore());
            }
            if (restaurant.getEggAllergyScore() != null) {
                restaurantDTO.setEggAllergyScore(restaurant.getEggAllergyScore());
            }
            if (restaurant.getDairyAllergyScore() != null) {
                restaurantDTO.setDairyAllergyScore(restaurant.getDairyAllergyScore());
            }

            restaurantDTOs.add(restaurantDTO);
        }

        return restaurantDTOs;
    }
}
