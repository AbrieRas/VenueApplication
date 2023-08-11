package com.restaurant.diningReview;

import com.restaurant.enums.Status;
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

    public DiningReviewController(
            final DiningReviewRepository diningReviewRepository,
            final UserRepository userRepository
    ) {
        this.diningReviewRepository = diningReviewRepository;
        this.userRepository = userRepository;
    }

    // Create
    @PostMapping(value = "/add")
    public ResponseEntity<DiningReview> addDiningReview(@RequestBody DiningReview diningReview) {
        boolean userRegistered = this.userRepository.existsByDisplayName(diningReview.getReviewer());

        if (!userRegistered) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        DiningReview diningReviewSaved = this.diningReviewRepository.save(diningReview);
        return ResponseEntity.status(HttpStatus.OK).body(diningReviewSaved);
    }

    // Read
    // ADMIN
    @GetMapping(value = "/get-received-reviews")
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
    // ADMIN
    @PutMapping(value = "/update/{id}")
    public ResponseEntity<DiningReview> updateDiningReview(@PathVariable Long id, @RequestParam Status status) {
        if (status == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Optional<DiningReview> diningReviewOptional = this.diningReviewRepository.findById(id.intValue());

        if (diningReviewOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        DiningReview diningReviewToUpdate = diningReviewOptional.get();
        diningReviewToUpdate.setStatus(status);

        DiningReview diningReviewUpdated = this.diningReviewRepository.save(diningReviewToUpdate);
        return ResponseEntity.status(HttpStatus.OK).body(diningReviewUpdated);
    }

    /*
     Delete
     NOT GOING TO BE IMPLEMENTED AT THIS STAGE
    */
}
