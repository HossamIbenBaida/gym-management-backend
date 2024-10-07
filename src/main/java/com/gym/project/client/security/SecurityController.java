package com.gym.project.client.security;

import com.gym.project.client.Dtos.LoginRequest;
import com.nimbusds.jwt.JWTClaimsSet;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/auth")
public class SecurityController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtEncoder jwtEncoder;
    private static final Logger logger = LoggerFactory.getLogger(SecurityController.class);
    @GetMapping("/profile")
    public Authentication authentication(Authentication authentication){
        return authentication;
    }

   @PostMapping("/login")
   public Map<String, String> login(@RequestBody LoginRequest loginRequest) {
       String username = loginRequest.getUsername();
       logger.debug("Login attempt received for user: {}", username);
       String password = loginRequest.getPassword();
       try {
           logger.debug("Attempting to authenticate user: {}", username);
           Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

           logger.info("User authenticated successfully: {}", username);

           logger.debug("Authorities for user {}:", username);
           authentication.getAuthorities().forEach(authority ->
                   logger.debug("Authority: {}", authority.getAuthority())
           );

           Instant instant = Instant.now();
           String scope = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(" "));
           logger.debug("Generated scope for user {}: {}", username, scope);

           logger.debug("Building JWT claims for user: {}", username);
           JwtClaimsSet jwtClaimsSet =
                   JwtClaimsSet.builder()
                           .issuedAt(instant)
                           .expiresAt(instant.plus(10, ChronoUnit.MINUTES))
                           .subject(username)
                           .claim("scope", scope)
                           .build();

           logger.debug("Encoding JWT token for user: {}", username);
           JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters.from(
                   JwsHeader.with(MacAlgorithm.HS512).build(),
                   jwtClaimsSet
           );
           String jwt = jwtEncoder.encode(jwtEncoderParameters).getTokenValue();

           logger.info("JWT token generated successfully for user: {}", username);
           return Map.of("token", jwt);
       } catch (Exception e) {
           logger.error("Authentication failed for user: {}", username, e);
           System.out.println("Login failed for user: " + username + " - " + e.getMessage());
           throw e;
       }
   }

}
