package com.gym.project.client;

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
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

//@SpringBootApplication( exclude = { SecurityAutoConfiguration.class } )
@EnableScheduling
@SpringBootApplication
public class ClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientApplication.class, args);
	}
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/").allowedOrigins("*");
			}
		};
	}

}

