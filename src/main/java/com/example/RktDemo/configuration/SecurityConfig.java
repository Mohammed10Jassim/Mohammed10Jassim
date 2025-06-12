package com.example.RktDemo.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JWTFilter jwtFilter;
    @Autowired
    private UserDetailsService userDetailsService;

    //Customizing Security FilterChain
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
       /* httpSecurity.csrf(customizer ->customizer.disable());
        httpSecurity.authorizeHttpRequests(request-> request.anyRequest().authenticated());
        httpSecurity.formLogin(Customizer.withDefaults());
        httpSecurity.httpBasic(Customizer.withDefaults());
        httpSecurity.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));*/

        /* Below one is called Builder pattern */

        return httpSecurity.csrf(customizer ->customizer.disable()).
        authorizeHttpRequests(request-> request.requestMatchers("/home/register","/home/login").permitAll().anyRequest().authenticated()).httpBasic(Customizer.withDefaults()).
        sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).
        addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class).build();
    }

    /* Hardcoding of  UserDetails Service by creating in an inmemory space

    @Bean
    public UserDetailsService userDetailsService()
    {
        UserDetails user1 = User.withDefaultPasswordEncoder().
                username("Safa").
                password("Safa10").
                roles("Admin").build();

        UserDetails user2 = User.withDefaultPasswordEncoder().
                username("Saba").
                password("Saba10").
                roles("Admin").build();


        return new InMemoryUserDetailsManager(user1,user2);
    }
*/

    //
    //
    //Custom Authentication Provider
    @Bean
    public AuthenticationProvider authenticationProvider()
    {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    //Implementation of JWT using AuthManager

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
       return  configuration.getAuthenticationManager();
    }
}
