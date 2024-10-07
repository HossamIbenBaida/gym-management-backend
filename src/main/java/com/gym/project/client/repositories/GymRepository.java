package com.gym.project.client.repositories;

import com.gym.project.client.Entities.Gym;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GymRepository extends JpaRepository<Gym , Long> {
    Gym findFirstByOrderByIdAsc();
}
