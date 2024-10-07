package com.gym.project.client.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainerDto extends UserDto{
    private String speciality;
    private String certificate;
}
