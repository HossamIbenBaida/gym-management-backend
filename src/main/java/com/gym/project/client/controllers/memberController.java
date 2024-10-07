package com.gym.project.client.controllers;

import com.gym.project.client.Dtos.GymDto;
import com.gym.project.client.Dtos.MemberDto;
import com.gym.project.client.Exceptions.MemberNotFoundException;
import com.gym.project.client.services.MemberService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("hasAuthority('SCOPE_ADMIN')")
@RequestMapping("/member")
public class memberController {

    private final MemberService memberService ;


    public memberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<MemberDto> getmemberByID(@PathVariable Long id) throws MemberNotFoundException {
        MemberDto memberDto = memberService.GetMemberById(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(memberDto) ;
    }
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<MemberDto>> getAllMembers () {
        List<MemberDto> memberList = memberService.GetAllMembers();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(memberList);
    }
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PostMapping
    public  ResponseEntity<MemberDto> addMember(@RequestBody MemberDto member){

        MemberDto AddedMember = memberService.AddMember(member);

        return ResponseEntity.status(HttpStatus.CREATED).body(AddedMember);
    }
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PutMapping("/{id}")
    public  ResponseEntity<MemberDto> updateMember(@RequestBody MemberDto memberdto ,@PathVariable  Long id) throws MemberNotFoundException {
        MemberDto UpdatedMember = memberService.UpdateMember(memberdto , id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(UpdatedMember);
    }
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @DeleteMapping("/{id}")
    public  ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        memberService.DeleteMember(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
    }
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping("/{id}/gym")
    public ResponseEntity<GymDto> getGym(@PathVariable Long id) throws MemberNotFoundException {
        GymDto gym = memberService.GetUserGym(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(gym) ;
    }
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PutMapping("/{id}/gym")
    public ResponseEntity<GymDto> UpdateUserGym(@PathVariable Long id , @RequestBody GymDto gymdto) throws MemberNotFoundException {
        GymDto gym = memberService.UpdateUserGym(id , gymdto);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(gym) ;
    }
}
