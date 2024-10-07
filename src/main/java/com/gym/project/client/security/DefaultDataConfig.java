package com.gym.project.client.security;


import com.gym.project.client.Entities.Admin;
import com.gym.project.client.Entities.Gym;
import com.gym.project.client.Entities.Member;
import com.gym.project.client.Entities.Trainer;
import com.gym.project.client.Enums.Membership;
import com.gym.project.client.Enums.Status;
import com.gym.project.client.repositories.AdminRepository;
import com.gym.project.client.repositories.GymRepository;
import com.gym.project.client.repositories.MemberRepository;
import com.gym.project.client.repositories.TrainerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;


@Configuration
public class DefaultDataConfig {

    @Bean
    public CommandLineRunner start(MemberRepository memberRepository,
                                   TrainerRepository trainerRepository,
                                   AdminRepository adminRepository,
                                   GymRepository gymRepository) {
        return args -> {
            List<Gym> gyms = gymRepository.findAll();
            if (gyms.isEmpty()) {
                Gym gym = new Gym();
                gym.setName("default");
                gym.setCity("agadir");
                gymRepository.save(gym);

                Gym defaultGym = gymRepository.findFirstByOrderByIdAsc();



                // Create trainers

                Trainer trainer = new Trainer();
                trainer.setFirstName("hossam");
                trainer.setLastName("ib");
                trainer.setEmail("trainer@gmail.com");
                trainer.setPassword("1234");
                trainer.setCertificate("Gold");
                trainer.setSpeciality("fitness");
                trainer.setGym(defaultGym);
                trainer.setRoles(List.of("TRAINER"));
                trainerRepository.save(trainer);

                // Create members
                Member member = new Member();
                member.setFirstName("default");
                member.setLastName("default");
                member.setEmail("member@gmail.com");
                member.setPassword("1234");
                member.setStartDate(LocalDate.now());
                member.setEndDate(LocalDate.now().plusDays(30));
                member.setType(Membership.STANDARD);
                member.setStatus(Status.ACTIVE);
                member.setGym(defaultGym);
                member.setRoles(List.of("MEMBER"));
                memberRepository.save(member);

                // Create admins
                Admin admin = new Admin();
                admin.setFirstName("adminName");
                admin.setLastName("DefaultAdmin");
                admin.setEmail("admin@gmail.com");
                admin.setPassword("1234");
                admin.setRoles(List.of("ADMIN", "MEMBER", "TRAINER")); // Admin has all roles
                adminRepository.save(admin);
            }
        };
    }
}
