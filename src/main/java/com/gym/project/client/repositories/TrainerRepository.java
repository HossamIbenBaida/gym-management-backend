package com.gym.project.client.repositories;

import com.gym.project.client.Entities.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainerRepository extends JpaRepository<Trainer, Long> {
}
