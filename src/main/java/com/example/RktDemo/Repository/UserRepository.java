package com.example.RktDemo.Repository;

import com.example.RktDemo.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<Users,Integer> {
    Users findByUsername(String username);


}
