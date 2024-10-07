package com.gym.project.client.services.Impl;

import com.gym.project.client.Dtos.GymDto;
import com.gym.project.client.Dtos.MemberDto;
import com.gym.project.client.Entities.Gym;
import com.gym.project.client.Entities.Member;
import com.gym.project.client.Enums.Membership;
import com.gym.project.client.Enums.Status;
import com.gym.project.client.Exceptions.MemberNotFoundException;
import com.gym.project.client.repositories.MemberRepository;
import com.gym.project.client.services.MemberService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
@Slf4j
public class MemberServiceImp implements MemberService {

    private final MemberRepository memberRepository;
    ModelMapper modelMapper = new ModelMapper();


    public MemberServiceImp(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public MemberDto AddMember(MemberDto memberDto) {
        Member memberToSave =  modelMapper.map(memberDto , Member.class);

        if(memberToSave.getType() == Membership.STANDARD) {
            memberToSave.setEndDate(Date.valueOf(memberToSave.getStartDate().plusDays(30)).toLocalDate());

        }
        else if (memberToSave.getType() == Membership.PREMIUM) {
            memberToSave.setEndDate(Date.valueOf(memberToSave.getStartDate().plusDays(90)).toLocalDate());
        }else if(memberToSave.getType() == Membership.GOLD){
            memberToSave.setEndDate(Date.valueOf(memberToSave.getStartDate().plusDays(365)).toLocalDate());
        }
        memberToSave.setRoles(Arrays.asList("MEMBER"));
        // Debug statement to check the password before setting
        System.out.println("Password before setting: " + memberDto.getPassword());

        // Set the password (this will encode it)
        memberToSave.setPassword(memberDto.getPassword());

        // Debug statement to check the encoded password
        System.out.println("Encoded Password: " + memberToSave.getPassword());
        Member savedModel = memberRepository.save(memberToSave);
        return modelMapper.map(savedModel , MemberDto.class);
    }

    @Override
    public MemberDto GetMemberById(Long id) throws MemberNotFoundException {
        Member m = memberRepository.findById(id).orElseThrow(()->new MemberNotFoundException("Member Not Found"));
        return modelMapper.map(m , MemberDto.class);
    }

    @Override
    public List<MemberDto> GetAllMembers() {
        List<Member> m = memberRepository.findAll();
        return m.stream().map( member -> modelMapper.map(member , MemberDto.class)).collect(Collectors.toList());
    }

    @Override
    public MemberDto UpdateMember(MemberDto memberdto, Long id) throws MemberNotFoundException {
        Member oldMember = memberRepository.findById(id).orElseThrow(()->new MemberNotFoundException("Member not fond"));
        oldMember.setFirstName(memberdto.getFirstName());
        oldMember.setLastName(memberdto.getLastName());
        oldMember.setEmail(memberdto.getEmail());
        oldMember.setPassword(memberdto.getPassword());
        oldMember.setStatus(memberdto.getStatus());
        oldMember.setType(memberdto.getType());
        oldMember.setStartDate(memberdto.getStartDate());

        if(oldMember.getType() == Membership.STANDARD) {
            oldMember.setEndDate(Date.valueOf(oldMember.getStartDate().plusDays(30)).toLocalDate());

        }
        else if (oldMember.getType() == Membership.PREMIUM) {
            oldMember.setEndDate(Date.valueOf(oldMember.getStartDate().plusDays(90)).toLocalDate());
        }else if(oldMember.getType() == Membership.GOLD){
            oldMember.setEndDate(Date.valueOf(oldMember.getStartDate().plusDays(365)).toLocalDate());
        }

        //oldMember.setEndDate(memberdto.getEndDate());
        Member  UpdatedMember = memberRepository.save(oldMember);
        return modelMapper.map(UpdatedMember , MemberDto.class);
    }

    @Override
    public void DeleteMember(Long id) {
        memberRepository.deleteById(id);

    }

    @Override
    public GymDto GetUserGym(Long id) throws MemberNotFoundException {
        Member m = memberRepository.findById(id).orElseThrow(()->new MemberNotFoundException("Member Not Found"));
        return  modelMapper.map(m.getGym(),GymDto.class);
    }

    @Override
    public GymDto UpdateUserGym(Long id, GymDto gymDto) throws MemberNotFoundException {
        Member m = memberRepository.findById(id).orElseThrow(()->new MemberNotFoundException("Member Not Found"));
        m.setGym(modelMapper.map(gymDto , Gym.class));
        Member savedMember = memberRepository.save(m);
        return  modelMapper.map(savedMember.getGym(),GymDto.class);
    }
    @Override
    @Scheduled(cron = "0 0 0 * * ?")
    public void checkAndUpdateMemberStatus() {
        LocalDate today = LocalDate.now();
        List<Member> members = memberRepository.findAll();
        for (Member member : members) {
            if (member.getEndDate().isBefore(today)) {
                member.setStatus(Status.INACTIVE);
                memberRepository.save(member);
            }
        }
    }

}
