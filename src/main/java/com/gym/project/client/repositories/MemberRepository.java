package com.gym.project.client.repositories;

import com.gym.project.client.Entities.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member , Long> {
}
