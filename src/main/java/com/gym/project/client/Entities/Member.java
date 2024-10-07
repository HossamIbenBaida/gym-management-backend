package com.gym.project.client.Entities;

import com.gym.project.client.Enums.Membership;
import com.gym.project.client.Enums.Status;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;


@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member extends User{
    @Enumerated(EnumType.STRING)
    private Membership type ;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Temporal(TemporalType.DATE)
    private LocalDate StartDate;
    @Temporal(TemporalType.DATE)
    private LocalDate EndDate;



}
