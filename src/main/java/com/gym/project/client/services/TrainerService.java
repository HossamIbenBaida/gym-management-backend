package com.gym.project.client.services;

import com.gym.project.client.Dtos.GymDto;
import com.gym.project.client.Dtos.MemberDto;
import com.gym.project.client.Dtos.TrainerDto;
import com.gym.project.client.Entities.Trainer;
import com.gym.project.client.Exceptions.MemberNotFoundException;
import com.gym.project.client.Exceptions.TranerNotFoundException;

import java.util.List;

public interface TrainerService {
    TrainerDto AddTrainer(TrainerDto trainerDto);
    TrainerDto GetTrainerById(Long id) throws TranerNotFoundException;
    List<TrainerDto> GetAllTrainer();
    TrainerDto UpdateTrainer(TrainerDto trainerDto, Long id) throws TranerNotFoundException;
    void DeleteTrainer(Long id);
    GymDto GetTrainerGym(Long id) ;
    GymDto UpdateTrainerGym(Long id, GymDto gymDto);
}
