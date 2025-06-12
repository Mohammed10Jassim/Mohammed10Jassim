package com.example.RktDemo.Service;

import com.example.RktDemo.Repository.UserRepository;
import com.example.RktDemo.dto.UserDTO;
import com.example.RktDemo.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public Users createUser(Users users)
    {
        return userRepository.save(users);
    }
    public Optional<Users> getUserDetailsbyId(int id) {
        return userRepository.findById(id);
    }
    public List<Users> getAllUserDetails()
    {
        return userRepository.findAll();
    }
    public Users updateUser(int id,Users updateUser)
    {
       Users users = userRepository.findById(id).orElseThrow(() -> new RuntimeException("user not found"));
       users.setMobile(updateUser.getMobile());

       users = userRepository.save(users);
       return users;
    }
    public void deleteUser(int id)
    {
         userRepository.deleteById(id);
    }

    public Users register(Users user)
    {
         user.setPassword(encoder.encode(user.getPassword()));
         return userRepository.save(user);
    }

    public String verify(Users user) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword()));
        if(authentication.isAuthenticated())
        {
            return jwtService.generateToken(user.getUserName());
        }
        return "Invalid Credentials!!!!";

        }

    public UserDTO convertToDTO(Users user) {
        return new UserDTO(user.getId(), user.getUserName(), user.getEmail(), user.getEmail());
    }

    public Users generate() {
        return userRepository.findById(5).orElseThrow();
    }
}

