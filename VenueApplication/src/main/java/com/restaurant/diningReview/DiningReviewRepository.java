package com.restaurant.diningReview;

import com.restaurant.enums.Status;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DiningReviewRepository extends CrudRepository<DiningReview, Long> {
    List<DiningReview> findDiningReviewByStatus(Status status);
    List<DiningReview> findDiningReviewByStatusAndRestaurantId(Status status, Long restaurantId);

    @Query("SELECT ROUND(AVG(r.peanutAllergyScore), 2) FROM DiningReview r")
    Float calculateAveragePeanutAllergyScore();
    @Query("SELECT ROUND(AVG(r.eggAllergyScore), 2) FROM DiningReview r")
    Float calculateAverageEggAllergyScore();
    @Query("SELECT ROUND(AVG(r.dairyAllergyScore), 2) FROM DiningReview r")
    Float calculateAverageDairyAllergyScore();
}
