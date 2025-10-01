package com.hackathon.recipesharingplatform.auth;

import com.hackathon.recipesharingplatform.auth.model.Chef;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChefRepository extends JpaRepository<Chef, Long> {
    Optional<Chef> findByUsername(String username);
}
