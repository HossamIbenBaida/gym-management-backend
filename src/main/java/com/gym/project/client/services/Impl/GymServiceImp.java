package com.gym.project.client.services.Impl;

import com.gym.project.client.Dtos.GymDto;
import com.gym.project.client.Dtos.MemberDto;
import com.gym.project.client.Entities.Gym;
import com.gym.project.client.Entities.Member;
import com.gym.project.client.Entities.User;
import com.gym.project.client.Exceptions.GymNotFoundException;
import com.gym.project.client.repositories.GymRepository;
import com.gym.project.client.services.GymService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class GymServiceImp implements GymService {
    private final GymRepository gymRepository;
    ModelMapper modelMapper = new ModelMapper();
    @Autowired
    public GymServiceImp(GymRepository gymRepository) {
        this.gymRepository = gymRepository;
    }
    @Override
    public GymDto getGymById(Long gymId) throws GymNotFoundException{
        Gym gym = gymRepository.findById(gymId).orElseThrow(()->new GymNotFoundException("Gym Not Found"));
        return  modelMapper.map(gym , GymDto.class) ;
    }

//    public List<MemberDto> getGymUsers(Long gymId){
//        Gym gym = gymRepository.findById(gymId).orElse(null);
//        List<User> res = gym != null ? gym.getUsers() : null;
//        assert res != null;
//        return res.stream().map((user)-> modelMapper.map(user , MemberDto.class)).collect(Collectors.toList());
//    }
    @Override
    public List<MemberDto> getGymMembers(Long gymId){
        Gym gym = gymRepository.findById(gymId).orElse(null);
        List<User> users = gym != null ? gym.getUsers() : null;
        assert users != null;

        return users.stream()
                .filter(user -> user instanceof Member)
                .map(member -> modelMapper.map(member, MemberDto.class))
                .collect(Collectors.toList());
    }
    @Override
    public List<GymDto> getAllGyms(){
        List<Gym> gyms = gymRepository.findAll();
        return gyms.stream().map(gym -> modelMapper.map(gym, GymDto.class)).collect(Collectors.toList());
    }

    @Override
    public GymDto createGym(GymDto gym) {
        Gym TosaveGym = modelMapper.map(gym , Gym.class);
        Gym saved = gymRepository.save(TosaveGym);
        return modelMapper.map(saved,GymDto.class);
    }

    @Override
    public GymDto updateGym(GymDto gym , Long id) throws GymNotFoundException {

        Gym old_gym = gymRepository.findById(id).orElseThrow(()->new GymNotFoundException("Gym Not Found"));

        old_gym.setName(gym.getName());
        old_gym.setCity(gym.getCity());
        gymRepository.save(old_gym);

        return modelMapper.map(old_gym , GymDto.class);

    }

    @Override
    public void deleteGym(Long gymId) throws GymNotFoundException {
        if (gymRepository.existsById(gymId)) {
            gymRepository.deleteById(gymId);
        } else {
            throw new GymNotFoundException("Gym Not Found");
        }
    }


}
