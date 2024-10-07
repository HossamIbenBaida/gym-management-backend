package com.gym.project.client.services;

import com.gym.project.client.Dtos.GymDto;
import com.gym.project.client.Dtos.MemberDto;
import com.gym.project.client.Exceptions.MemberNotFoundException;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

public interface MemberService {

    MemberDto AddMember(MemberDto member);
    MemberDto GetMemberById(Long id) throws MemberNotFoundException;
    List<MemberDto> GetAllMembers();
   // MemberDto UpdateMember(MemberDto member );
    MemberDto UpdateMember(MemberDto memberdto, Long id) throws MemberNotFoundException;
    void DeleteMember(Long id);
    GymDto GetUserGym(Long id) throws MemberNotFoundException;

    GymDto UpdateUserGym(Long id, GymDto gymDto) throws MemberNotFoundException;

    @Scheduled(cron = "0 0 0 * * ?")
    void checkAndUpdateMemberStatus();
}
