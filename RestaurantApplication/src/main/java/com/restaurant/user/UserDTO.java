package com.restaurant.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
    private String displayName;
    private String city;
    private String state;
    private String zipCode;
    private Boolean peanutAllergyInterest;
    private Boolean eggAllergyInterest;
    private Boolean dairyAllergyInterest;
}
