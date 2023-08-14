package com.restaurant.diningReview;

import com.restaurant.enums.Status;
import com.restaurant.restaurant.Restaurant;
import com.restaurant.restaurant.RestaurantRepository;
import com.restaurant.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/dining-reviews")
public class DiningReviewController {
    private final DiningReviewRepository diningReviewRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    public DiningReviewController(
            final DiningReviewRepository diningReviewRepository,
            final UserRepository userRepository,
            final RestaurantRepository restaurantRepository
    ) {
        this.diningReviewRepository = diningReviewRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
    }

    // Create
    @PostMapping(value = "/add")
    public ResponseEntity<DiningReview> addDiningReview(@RequestBody DiningReview diningReview) {
        boolean userRegistered = this.userRepository.existsByDisplayName(diningReview.getReviewer());

        // Return if user isn't registered
        if (!userRegistered) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // Find restaurant by id
        Optional<Restaurant> restaurantOptional = this.restaurantRepository.findById(
                diningReview.getRestaurantId()
        );

        // Return if restaurant id not found
        if (restaurantOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // Update restaurant score
        Restaurant restaurantToUpdate = restaurantOptional.get();
        Restaurant restaurantToSave = this.updateRestaurantScores(restaurantToUpdate, diningReview);
        this.restaurantRepository.save(restaurantToSave);

        // Add dining review
        diningReview.setStatus(Status.RECEIVED);
        DiningReview diningReviewSaved = this.diningReviewRepository.save(diningReview);

        return ResponseEntity.status(HttpStatus.OK).body(diningReviewSaved);
    }

    // Read
    @GetMapping(value = "/admin/get-received-reviews")
    public ResponseEntity<List<DiningReview>> getDiningReviewsByReceived() {
        List<DiningReview> diningReviewsReceived = this.diningReviewRepository.findDiningReviewByStatus(Status.RECEIVED);
        return ResponseEntity.status(HttpStatus.OK).body(diningReviewsReceived);
    }

    @GetMapping(value = "/restaurant/{id}")
    public ResponseEntity<List<DiningReview>> getDiningReviewsApprovedByRestaurant(@PathVariable Long id) {
        List<DiningReview> diningReviewsFound = this.diningReviewRepository.findDiningReviewByStatusAndRestaurantId(
                Status.ACCEPTED,
                id
        );

        if (diningReviewsFound == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(diningReviewsFound);
    }

    // Update
    @PutMapping(value = "/admin/update/{id}")
    public ResponseEntity<DiningReview> updateDiningReview(@PathVariable Long id, @RequestParam Status status) {
        if (status == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        // Find dining review by id
        Optional<DiningReview> diningReviewOptional = this.diningReviewRepository.findById(id);

        // Return if dining review id not found
        if (diningReviewOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // Update dining review
        DiningReview diningReviewToUpdate = diningReviewOptional.get();
        diningReviewToUpdate.setStatus(status);

        // Find restaurant by id
        Optional<Restaurant> restaurantOptional = this.restaurantRepository.findById(
                diningReviewToUpdate.getRestaurantId()
        );

        // Return if restaurant id not found
        if (restaurantOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // Update restaurant score
        Restaurant restaurantToUpdate = restaurantOptional.get();
        Restaurant restaurantToSave = this.updateRestaurantScores(restaurantToUpdate, diningReviewToUpdate);
        this.restaurantRepository.save(restaurantToSave);

        // Update dining review
        DiningReview diningReviewUpdated = this.diningReviewRepository.save(diningReviewToUpdate);

        return ResponseEntity.status(HttpStatus.OK).body(diningReviewUpdated);
    }

    /*
     Delete
     NOT GOING TO BE IMPLEMENTED AT THIS STAGE
    */

    private Restaurant updateRestaurantScores(Restaurant restaurant, DiningReview diningReview) {
        if (diningReview.getPeanutAllergyScore() != null) {
            Float averagePeanutAllergyScore = this.diningReviewRepository.calculateAveragePeanutAllergyScore();
            restaurant.setPeanutAllergyScore(averagePeanutAllergyScore);
        }
        if (diningReview.getEggAllergyScore() != null) {
            Float averageEggAllergyScore = this.diningReviewRepository.calculateAverageEggAllergyScore();
            restaurant.setEggAllergyScore(averageEggAllergyScore);
        }
        if (diningReview.getDairyAllergyScore() != null) {
            Float averageDairyAllergyScore = this.diningReviewRepository.calculateAverageDairyAllergyScore();
            restaurant.setDairyAllergyScore(averageDairyAllergyScore);
        }

        return restaurant;
    }
}
