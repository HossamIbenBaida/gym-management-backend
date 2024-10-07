package com.gym.project.client.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Trainer extends User {
    private String speciality;
    private String certificate;
}
