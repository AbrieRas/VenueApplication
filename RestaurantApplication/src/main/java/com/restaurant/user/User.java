package com.restaurant.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Table(name = "USER2222")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "DISPLAY_NAME", unique = true)
    private String displayName;

    @Column(name = "CITY")
    private String city;

    @Column(name = "STATE")
    private String state;

    @Column(name = "ZIP_CODE")
    private String zipCode;

    @Column(name = "PEANUT_ALLERGY_INTEREST")
    private Boolean peanutAllergyInterest;

    @Column(name = "EGG_ALLERGY_INTEREST")
    private Boolean eggAllergyInterest;

    @Column(name = "DAIRY_ALLERGY_INTEREST")
    private Boolean dairyAllergyInterest;
}
