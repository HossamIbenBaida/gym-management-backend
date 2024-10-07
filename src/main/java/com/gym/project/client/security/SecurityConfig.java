package com.gym.project.client.security;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.context.annotation.Bean;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.crypto.spec.SecretKeySpec;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Value("${jwt.secret}")
    private String secretKey;
//    @Bean
//    public InMemoryUserDetailsManager inMemoryUserDetailsManager(){
//        PasswordEncoder passwordEncoder = passwordEncoder();
//        return new InMemoryUserDetailsManager(User.withUsername("member").password( passwordEncoder.encode("12345")).authorities("MEMBER").build(),
//                User.withUsername("admin").password( passwordEncoder.encode("12345")).authorities("ADMIN").build(),
//                User.withUsername("trainer").password( passwordEncoder.encode("12345")).authorities("TRAINER").build());
//
//    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(crsf->crsf.disable())
                .sessionManagement(sm->sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(ar->ar.requestMatchers("/auth/login").permitAll())
                .authorizeHttpRequests(ar->ar.requestMatchers("/v3/api-docs/**").permitAll())
                .authorizeHttpRequests(ar->ar.requestMatchers("/swagger-ui/**").permitAll())
                .authorizeHttpRequests(ar->ar.anyRequest().authenticated())
                .oauth2ResourceServer(oa->oa.jwt(Customizer.withDefaults()))
                .cors(Customizer.withDefaults()) // Enable CORS
                .logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout/**"))
                .logoutSuccessUrl("/auth/login/**")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID"))
                .build();

    }

    @Bean
    JwtEncoder jwtEncoder(){
        //String secretKey = "XcTtmEsUFuAtMP64ImqsKsEfoLf3dnWnG3lvlWobxRLF7D0IoZV79zxCPA3vk5Na";
        return new NimbusJwtEncoder(new ImmutableSecret<>(secretKey.getBytes()));
    }

    @Bean
    JwtDecoder jwtDecoder (){
       // String secretKey = "XcTtmEsUFuAtMP64ImqsKsEfoLf3dnWnG3lvlWobxRLF7D0IoZV79zxCPA3vk5Na";
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(),"RSA");
        return NimbusJwtDecoder.withSecretKey(secretKeySpec).macAlgorithm(MacAlgorithm.HS512).build();
    }
    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return new ProviderManager(daoAuthenticationProvider);
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:6061"); // Allow specific origin
        configuration.addAllowedMethod("*"); // Allow all HTTP methods
        configuration.addAllowedHeader("*"); // Allow all headers
        configuration.setAllowCredentials(true); // Allow credentials (cookies, authorization headers, etc.)

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Apply CORS to all paths
        return source;
    }

    @Bean
    public CorsFilter corsFilter() {
        return new CorsFilter(corsConfigurationSource());
    }

}
