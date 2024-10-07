package com.gym.project.client.services;

import com.gym.project.client.Dtos.GymDto;
import com.gym.project.client.Dtos.MemberDto;
import com.gym.project.client.Exceptions.GymNotFoundException;

import java.util.List;

public interface GymService {
    GymDto getGymById(Long gymId) throws GymNotFoundException;
    List<MemberDto> getGymMembers(Long gymId);
    List<GymDto> getAllGyms();
    GymDto createGym(GymDto gym);
    GymDto updateGym(GymDto gym , Long id) throws GymNotFoundException;
    void deleteGym(Long gymId) throws GymNotFoundException;
}
