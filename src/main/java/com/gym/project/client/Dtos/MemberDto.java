package com.gym.project.client.Dtos;

import com.gym.project.client.Enums.Membership;
import com.gym.project.client.Enums.Status;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@EqualsAndHashCode(callSuper = true)
@Data
public class MemberDto extends UserDto{

    private Membership type ;
    private Status status;
    @Temporal(TemporalType.DATE)
    private LocalDate StartDate;
    @Temporal(TemporalType.DATE)
    private LocalDate EndDate;

}
