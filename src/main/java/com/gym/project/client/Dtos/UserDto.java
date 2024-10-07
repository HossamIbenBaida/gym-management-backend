package com.gym.project.client.Dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gym.project.client.Entities.Gym;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    @Size(min = 6 , message = "at least 6 characters")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long gymId;
    private String gymName;
    private String gymCity;
}
