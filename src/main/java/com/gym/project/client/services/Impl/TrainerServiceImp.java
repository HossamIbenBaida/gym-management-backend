package com.gym.project.client.services.Impl;

import com.gym.project.client.Dtos.GymDto;
import com.gym.project.client.Dtos.TrainerDto;
import com.gym.project.client.Entities.Gym;
import com.gym.project.client.Entities.Trainer;
import com.gym.project.client.Exceptions.TranerNotFoundException;
import com.gym.project.client.repositories.TrainerRepository;
import com.gym.project.client.services.TrainerService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TrainerServiceImp implements TrainerService {

    private final TrainerRepository trainerRepository;
    ModelMapper modelMapper = new ModelMapper();

    public TrainerServiceImp(TrainerRepository trainerRepository) {
        this.trainerRepository = trainerRepository;
    }

    @Override
    public TrainerDto AddTrainer(TrainerDto trainerDto) {
        Trainer trainer = modelMapper.map(trainerDto , Trainer.class);
        trainer.setRoles(Arrays.asList("TRAINER","MEMBER"));
        Trainer SavedTrainer = trainerRepository.save(trainer);
        return  modelMapper.map(SavedTrainer , TrainerDto.class);
    }

    @Override
    public TrainerDto GetTrainerById(Long id) throws TranerNotFoundException {
        Trainer trainer = trainerRepository.findById(id).orElseThrow(()-> new TranerNotFoundException("trainer not found"));
        return modelMapper.map(trainer , TrainerDto.class);
    }

    @Override
    public List<TrainerDto> GetAllTrainer() {
        List<Trainer> trainerList = trainerRepository.findAll();
        return trainerList.stream().map(trainer -> modelMapper.map(trainer , TrainerDto.class) ).collect(Collectors.toList());
    }

    @Override
    public TrainerDto UpdateTrainer(TrainerDto trainerDto, Long id) throws TranerNotFoundException {
        Trainer trainertoUpdate = trainerRepository.findById(id).orElseThrow(()-> new TranerNotFoundException("trainer not found"));
        //BeanUtils.copyProperties(trainerDto, trainertoUpdate);
        trainertoUpdate.setFirstName(trainerDto.getFirstName());
        trainertoUpdate.setLastName(trainerDto.getLastName());
        trainertoUpdate.setEmail(trainerDto.getEmail());
        trainertoUpdate.setPassword(trainerDto.getPassword());
        //trainertoUpdate.setGym(modelMapper.map(trainerDto.getGym(), Gym.class));
        trainertoUpdate.setCertificate(trainerDto.getCertificate());
        trainertoUpdate.setSpeciality(trainerDto.getSpeciality());
        Trainer t= trainerRepository.save(trainertoUpdate);
        return modelMapper.map(t,TrainerDto.class);
    }

    @Override
    public void DeleteTrainer(Long id) {
        trainerRepository.deleteById(id);

    }

    @Override
    public GymDto GetTrainerGym(Long id) {
        return null;
    }

    @Override
    public GymDto UpdateTrainerGym(Long id, GymDto gymDto) {
        return null;
    }
}
