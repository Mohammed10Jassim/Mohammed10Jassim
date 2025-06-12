package com.example.RktDemo.Controller;


import java.util.List;
import java.util.Optional;

import com.example.RktDemo.dto.UserDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import com.example.RktDemo.Service.UserService;
import com.example.RktDemo.model.Users;

@RestController
@RequestMapping("/home")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String healthCheck()
    {
        return "Ok";
    }

    @GetMapping("/welcome")
    public String greet()
    {
        return "Welcome Page!!!!";
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody Users users)
    {
        if(users != null){
            Users resultUser = userService.createUser(users);
            return new ResponseEntity<>(userService.convertToDTO(resultUser), HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>("User is null.", HttpStatus.NO_CONTENT);
        }

    }
    @GetMapping("/generate")
    public ResponseEntity<?> generate(){
        return ResponseEntity.ok(userService.generate());
    }
    @GetMapping("/allusers")
    public ResponseEntity<List<UserDTO>> getAllUserDetails(){

        List<Users> allUsers = userService.getAllUserDetails();
        List<UserDTO> userDTOS = allUsers.stream()
                .map(userService::convertToDTO)
                .toList();
        return new ResponseEntity<>(userDTOS,HttpStatus.OK);
    }
    @GetMapping("/user/{id}")
    public ResponseEntity<UserDTO> getUserDetailsById(@PathVariable int id)
    {
        Optional<Users> optionalUser = userService.getUserDetailsbyId(id);
        if(optionalUser.isPresent()){
            return new ResponseEntity<>(userService.convertToDTO(optionalUser.get()),HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/update")
    public ResponseEntity<UserDTO> updateUser(@PathVariable int id,@RequestBody Users updateUser)
    {
        Users updatedUser = userService.updateUser(id,updateUser);
        return new ResponseEntity<>(userService.convertToDTO(updatedUser),HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String>deleteUser(@PathVariable int id)
    {
        userService.deleteUser(id);
        return new ResponseEntity<>("Deleted",HttpStatus.OK);
    }
    @GetMapping("/csrf")
    public CsrfToken gettoken(HttpServletRequest request)
    {
        return (CsrfToken) request.getAttribute("_csrf");
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody Users user)
    {
        Users registeredUser = userService.register(user);
        return new ResponseEntity<>(userService.convertToDTO(registeredUser),HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Users user)
    {
        return new ResponseEntity<>(userService.verify(user),HttpStatus.OK);
    }


}
