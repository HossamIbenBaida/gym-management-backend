package com.gym.project.client.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Gym {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name ;
    private  String City;
    @OneToMany(mappedBy ="gym" , fetch = FetchType.LAZY)
    private List<User> users;
}
