package com.restaurant.diningReview;

import com.restaurant.enums.Status;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DiningReviewRepository extends CrudRepository<DiningReview, Integer> {
    List<DiningReview> findDiningReviewByStatus(Status status);
    List<DiningReview> findDiningReviewByStatusAndRestaurantId(Status status, Long restaurantId);
    Boolean existsById(Long id);
}
