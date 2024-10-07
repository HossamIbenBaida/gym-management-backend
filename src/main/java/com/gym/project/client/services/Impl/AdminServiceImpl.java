package com.gym.project.client.services.Impl;

import com.gym.project.client.Dtos.AdminDto;
import com.gym.project.client.Dtos.GymDto;
import com.gym.project.client.Entities.Admin;
import com.gym.project.client.Entities.Gym;
import com.gym.project.client.Exceptions.AdminNotFoundException;
import com.gym.project.client.repositories.AdminRepository;
import com.gym.project.client.services.AdminService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    ModelMapper modelMapper = new ModelMapper();
    private final AdminRepository adminRepository ;

    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public AdminDto AddAdmin(AdminDto adminDto) {
        Admin admin = modelMapper.map(adminDto , Admin.class);
        admin.setRoles(Arrays.asList("ADMIN" , "MEMBER","TRAINER"));
        Admin savedAdmin = adminRepository.save(admin);
        return modelMapper.map(savedAdmin , AdminDto.class);
    }

    @Override
    public AdminDto UpdateAdmin(AdminDto adminDto, Long id) throws AdminNotFoundException {
        Admin adminToUpdate = adminRepository.findById(id).orElseThrow(()->new AdminNotFoundException("AdminNotFound"));
        BeanUtils.copyProperties(adminDto,adminToUpdate);
        Admin SavedAdmin = adminRepository.save(adminToUpdate);
        return modelMapper.map(SavedAdmin , AdminDto.class);
    }

    @Override
    public AdminDto ShowAdmin(Long id) throws AdminNotFoundException {
        Admin admin = adminRepository.findById(id).orElseThrow(()->new AdminNotFoundException("AdminNotFound"));
    return modelMapper.map(admin,AdminDto.class);
    }

    @Override
    public Void DeleteAdmin(Long id) {
        adminRepository.deleteById(id);
        return null;
    }

    @Override
    public GymDto AdminGyms(Long id) throws AdminNotFoundException {
        Admin admin = adminRepository.findById(id).orElseThrow(()->new AdminNotFoundException("AdminNotFound"));

        Gym gym = admin.getGym();
        return modelMapper.map(gym , GymDto.class);
    }
}
