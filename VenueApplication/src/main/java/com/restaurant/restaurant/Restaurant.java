package com.restaurant.restaurant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "RESTAURANT")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "ZIP_CODE")
    private String zipCode;

    // Scores are on a scale of 1 to 5
    @Column(name = "PEANUT_ALLERGY_SCORE")
    private Float peanutAllergyScore;

    @Column(name = "EGG_ALLERGY_SCORE")
    private Float eggAllergyScore;

    @Column(name = "DAIRY_ALLERGY_SCORE")
    private Float dairyAllergyScore;
}
