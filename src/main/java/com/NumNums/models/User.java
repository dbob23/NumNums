package com.NumNums.models;

import org.hibernate.validator.constraints.Length;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "user")
public class User {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "user_id")
    private int id;

    @Column(unique = true)
    @NotEmpty(message = "Please provide your name.")
    private String username;

    @Column(name = "email")
    @Email(message = "Please provide a valid email.")
    @NotEmpty(message = "Please provide an email.")
    private String email;

    @Column(name = "password")
    @Length(min = 5, message = "Your password must have at least 5 characters.")
    @NotEmpty(message = "Please provide your password.")
    private String password;


    @Column(name = "active")
    private int active;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name= "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set <Role> roles;


    public User() {
    }

    public User(Integer id, String username, String password, String email, Set roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.active = 0;
        this.roles = roles;

    }



    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
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

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }


    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(HashSet<com.NumNums.models.Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User: " +
                "id=" + id +
                ", username= " + username +
                ", email= " + email;
    }
}


