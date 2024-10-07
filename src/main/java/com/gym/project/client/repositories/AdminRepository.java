package com.gym.project.client.repositories;

import com.gym.project.client.Entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin , Long> {

    Optional<Admin> findByEmail(String email);

}
