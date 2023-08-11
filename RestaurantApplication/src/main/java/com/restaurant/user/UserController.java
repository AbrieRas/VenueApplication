package com.restaurant.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/users")
public class UserController {
    private final UserRepository userRepository;

    public UserController(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Create
    @PostMapping(value = "/add")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        boolean userDisplayNameExists = this.userRepository.existsByDisplayName(user.getDisplayName());

        if (userDisplayNameExists) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }

        User userSaved = this.userRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body(userSaved);
    }

    // Read
    @GetMapping(value = "/get/{displayName}")
    public ResponseEntity<UserDTO> getUser(@PathVariable String displayName) {
        boolean userDisplayNameExists = this.userRepository.existsByDisplayName(displayName);

        if (!userDisplayNameExists) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        User userFound = this.userRepository.findDistinctByDisplayName(displayName);
        UserDTO userDTO = createDTO(userFound);
        return ResponseEntity.status(HttpStatus.OK).body(userDTO);
    }

    @GetMapping(value = "/exists/{displayName}")
    public ResponseEntity<Boolean> getUserExistence(@PathVariable String displayName) {
        boolean userDisplayNameExists = this.userRepository.existsByDisplayName(displayName);

        if (!userDisplayNameExists) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(userDisplayNameExists);
        }

        return ResponseEntity.status(HttpStatus.OK).body(userDisplayNameExists);
    }

    // Update
    @PutMapping(value = "/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        Optional<User> userOptional = this.userRepository.findById(id.intValue());

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        User userToUpdate = userOptional.get();
        User userUpdated = updateUser(userToUpdate, user);
        return ResponseEntity.status(HttpStatus.OK).body(userUpdated);
    }

    /*
     Delete
     NOT GOING TO BE IMPLEMENTED AT THIS STAGE
    */

    private User updateUser(User userToUpdate, User userWithUpdate) {
        if (userWithUpdate.getCity() != null) {
            userToUpdate.setCity(userWithUpdate.getCity());
        }

        if (userWithUpdate.getState() != null) {
            userToUpdate.setState(userWithUpdate.getState());
        }

        if (userWithUpdate.getZipCode() != null) {
            userToUpdate.setZipCode(userWithUpdate.getZipCode());
        }

        if (userWithUpdate.getPeanutAllergyInterest() != null) {
            userToUpdate.setPeanutAllergyInterest(userWithUpdate.getPeanutAllergyInterest());
        }

        if (userWithUpdate.getEggAllergyInterest() != null) {
            userToUpdate.setDairyAllergyInterest(userWithUpdate.getDairyAllergyInterest());
        }

        return userToUpdate;
    }

    private UserDTO createDTO(User user) {
        UserDTO userDTO = new UserDTO();

        userDTO.setDisplayName(user.getDisplayName());
        userDTO.setCity(user.getCity());
        userDTO.setState(user.getState());
        userDTO.setZipCode(user.getZipCode());
        userDTO.setPeanutAllergyInterest(user.getPeanutAllergyInterest());
        userDTO.setEggAllergyInterest(user.getEggAllergyInterest());
        userDTO.setDairyAllergyInterest(user.getDairyAllergyInterest());

        return userDTO;
    }
}
