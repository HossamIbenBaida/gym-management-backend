package com.gym.project.client.controllers;

import com.gym.project.client.Dtos.AdminDto;
import com.gym.project.client.Dtos.GymDto;
import com.gym.project.client.Exceptions.AdminNotFoundException;
import com.gym.project.client.services.AdminService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@PreAuthorize("hasAuthority('SCOPE_ADMIN')")
@RequestMapping("/admin")
public class adminController {
    private final AdminService adminService;

    public adminController(AdminService adminService) {
        this.adminService = adminService;
    }


    @GetMapping("/{id}")
    ResponseEntity<AdminDto> showAdmin (@PathVariable Long id) throws AdminNotFoundException {
        AdminDto adminDto = adminService.ShowAdmin(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(adminDto);
    }

    @GetMapping("/{id}/gym")
    ResponseEntity<GymDto> getAdminGyms (@PathVariable long id ) throws AdminNotFoundException {
       GymDto gym = adminService.AdminGyms(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(gym);
    }

    @PostMapping
    ResponseEntity<AdminDto> addAdmin(@RequestBody AdminDto Admin){
        AdminDto addedAdmin = adminService.AddAdmin(Admin);
        return  ResponseEntity.status(HttpStatus.CREATED).body(addedAdmin);
    }
    @PutMapping("/{id}")
    ResponseEntity<AdminDto> updateAdmin(@RequestBody AdminDto admin, @PathVariable Long id) throws AdminNotFoundException {
        AdminDto updatedAdmin = adminService.UpdateAdmin(admin , id);
        return  ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedAdmin);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteAdmin(@PathVariable Long id){
        adminService.DeleteAdmin(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
    }
}
