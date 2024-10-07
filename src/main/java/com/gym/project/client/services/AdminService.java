package com.gym.project.client.services;

import com.gym.project.client.Dtos.AdminDto;
import com.gym.project.client.Dtos.GymDto;
import com.gym.project.client.Exceptions.AdminNotFoundException;

import java.util.List;

public interface AdminService {
    AdminDto AddAdmin(AdminDto adminDto);
    AdminDto UpdateAdmin(AdminDto adminDto , Long id) throws AdminNotFoundException;
    AdminDto ShowAdmin(Long id) throws AdminNotFoundException;
    Void DeleteAdmin(Long id);
    GymDto AdminGyms(Long id) throws AdminNotFoundException;
}
