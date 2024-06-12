package com.example.Education_Java4b.repos;

import com.example.Education_Java4b.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String name);
    Optional<User> findByEmail(String email);
}