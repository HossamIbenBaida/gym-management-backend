package com.gym.project.client.controllers;

import com.gym.project.client.Dtos.GymDto;
import com.gym.project.client.Dtos.TrainerDto;
import com.gym.project.client.Entities.Trainer;
import com.gym.project.client.Exceptions.TranerNotFoundException;
import com.gym.project.client.services.TrainerService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@RequestMapping("/trainers")
@AllArgsConstructor
public class TrainerController {
    private final TrainerService trainerService ;

    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_TRAINER')")
    @GetMapping
    ResponseEntity<List<TrainerDto>> GetAllTrainers(){
        List<TrainerDto> trainers = trainerService.GetAllTrainer();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(trainers);
    }
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PostMapping
    ResponseEntity<TrainerDto> AddTrainer(@RequestBody TrainerDto trainerDto){
        TrainerDto savedTrainer = trainerService.AddTrainer(trainerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTrainer);
    }
    @PreAuthorize("hasAnyAuthority('SCOPE_MEMBER','SCOPE_ADMIN', 'SCOPE_TRAINER')")
    @GetMapping("/{id}")
    ResponseEntity<TrainerDto> GetTrainerById(@PathVariable Long id) throws TranerNotFoundException {
        TrainerDto trainerDto = trainerService.GetTrainerById(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(trainerDto);
    }
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PutMapping("/{id}")
    ResponseEntity<TrainerDto> UpdateTrainer(@PathVariable Long id , @RequestBody TrainerDto trainerDto) throws TranerNotFoundException {
        TrainerDto UpdatedTrainer = trainerService.UpdateTrainer( trainerDto , id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(UpdatedTrainer);
    }
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @DeleteMapping("/{id}")
    ResponseEntity<Void> DeleteTrainer(@PathVariable Long id){
        trainerService.DeleteTrainer(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
    }
    @PreAuthorize("hasAnyAuthority('SCOPE_MEMBER','SCOPE_ADMIN', 'SCOPE_TRAINER')")
    @GetMapping("/{id}/gym")
    ResponseEntity<GymDto> GetTrainerGym(@PathVariable Long id) {
        GymDto gym = trainerService.GetTrainerGym(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(gym);
    }

}
