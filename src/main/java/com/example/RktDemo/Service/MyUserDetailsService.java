package com.example.RktDemo.Service;

import com.example.RktDemo.Repository.UserRepository;
import com.example.RktDemo.model.UserPrincipal;
import com.example.RktDemo.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByUsername(username);
        if(user == null)
        {
            System.out.println("User Not Found !!!!!");
            throw new UsernameNotFoundException("User Not Found !!!!");
        }
        return new UserPrincipal(user);
    }
}
