package com.restaurant.diningReview;

import com.restaurant.enums.Status;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "DINING_REVIEW")
public class DiningReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "REVIEWER")
    private String reviewer;

    @NotNull
    @Column(name = "RESTAURANT_ID")
    private Long restaurantId;

    // Scores are on a scale of 1 to 5
    @Column(name = "PEANUT_ALLERGY_SCORE")
    private Integer peanutAllergyScore;

    @Column(name = "EGG_ALLERGY_SCORE")
    private Integer eggAllergyScore;

    @Column(name = "DAIRY_ALLERGY_SCORE")
    private Integer dairyAllergyScore;

    @Column(name = "COMMENTARY")
    private String commentary;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private Status status;
}
