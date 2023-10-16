package com.restaurant.user;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    Boolean existsByDisplayName(String displayName);
    User findDistinctByDisplayName(String displayName);
}
