package com.example.Education_Java4b.repos;

import com.example.Education_Java4b.models.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface WorkerRepository extends JpaRepository<Worker, Long>{
    Optional<Worker> findByName(String name);
    Optional<Worker> findByEmail(String email);
}