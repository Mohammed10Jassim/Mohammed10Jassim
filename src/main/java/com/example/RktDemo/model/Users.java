package com.example.RktDemo.model;


import jakarta.persistence.*;


@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name ="username")
    private String username;

    public Users(String username, String password, String email, String mobile) {

        this.username = username;
        this.password = password;
        this.email = email;
        this.mobile = mobile;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Users(int id, String username, String password, String email, String mobile) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.mobile = mobile;
    }

    private String password;
    private String email;
    private String mobile;


    public Users() {
    }
}






