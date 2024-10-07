package com.gym.project.client.mapper;

import com.gym.project.client.Dtos.UserDto;
import com.gym.project.client.Entities.Gym;
import com.gym.project.client.Entities.User;
import com.gym.project.client.repositories.GymRepository;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class UserMappings {

    @Autowired
    private GymRepository gymRepository;

    public PropertyMap<User, UserDto> userToUserDtoMap() {
        return new PropertyMap<User, UserDto>() {
            @Override
            protected void configure() {
                map().setGymName(source.getGym().getName());
                map().setGymCity(source.getGym().getCity());
            }
        };
    }

    public PropertyMap<UserDto, User> userDtoToUserMap() {
        return new PropertyMap<UserDto, User>() {
            @Override
            protected void configure() {
                if (source.getGymId() != null) {
                    Optional<Gym> gymOptional = gymRepository.findById(source.getGymId());
                    gymOptional.ifPresent(gym -> map().setGym(gym));
                }
            }
        };
    }
}
