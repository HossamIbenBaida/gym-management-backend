package com.gym.project.client.controllers;


import com.gym.project.client.Dtos.GymDto;
import com.gym.project.client.Dtos.MemberDto;
import com.gym.project.client.Exceptions.GymNotFoundException;
import com.gym.project.client.services.GymService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/gyms")
public class gymController {

    private final GymService gymService;

    @Autowired
    public gymController(GymService gymService) {
        this.gymService = gymService;
    }
    @PreAuthorize("hasAnyAuthority('SCOPE_MEMBER', 'SCOPE_TRAINER')")
    @GetMapping("/{gymID}")
    public ResponseEntity<GymDto> getGym( @PathVariable Long gymID) throws GymNotFoundException {
        GymDto gymDto = gymService.getGymById(gymID);
        return ResponseEntity.ok(gymDto);
    }
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping("{gymID}/users")
    public ResponseEntity<List<MemberDto>> getGymUsers( @PathVariable Long gymID){
        List<MemberDto> users = gymService.getGymMembers(gymID);
        return ResponseEntity.ok(users);
    }
    @PreAuthorize("hasAnyAuthority('SCOPE_MEMBER', 'SCOPE_TRAINER')")
    @GetMapping
    public  ResponseEntity<List<GymDto>> getAllGyms(){
        List<GymDto> gyms = gymService.getAllGyms();
        return  ResponseEntity.ok(gyms);
    }
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PostMapping
    public ResponseEntity<GymDto> createGym(@RequestBody GymDto gymDto){
        GymDto createdGym = gymService.createGym(gymDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdGym);
    }
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PutMapping("{gymID}")
    public  ResponseEntity<GymDto> updateGym (@RequestBody GymDto gymDto , @PathVariable Long gymID) throws GymNotFoundException {
        GymDto updatedGym = gymService.updateGym(gymDto , gymID);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedGym);
    }
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @DeleteMapping("{gymID}")
    public ResponseEntity<Void> deleteGym(@PathVariable Long gymID) throws GymNotFoundException {
        gymService.deleteGym(gymID);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
    }

}
