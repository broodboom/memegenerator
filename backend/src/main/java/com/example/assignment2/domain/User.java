package com.example.assignment2.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User extends BaseEntity {
    
    /**
     *
     */
    private static final long serialVersionUID = 7662525133363601456L;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
        name = "user_achievements", 
        joinColumns = { @JoinColumn(name = "userid") }, 
        inverseJoinColumns = { @JoinColumn(name = "achievementid") }
    )
    Set<Achievement> achievements = new HashSet<>();
    
    @Column(name = "username", nullable = false)
    @Getter
    @Setter
    @NotNull
    private String username;

    @Column(name = "password", nullable = false)
    @Getter
    @Setter
    @NotNull
    private String password;

    @Column(name = "points", nullable = false)
    @Getter
    @Setter
    @NotNull
    private int points;

    @Column(name = "email", nullable = false)
    @Getter
    @Setter
    @NotNull
    private String email;

    @Column(name = "role", nullable = false)
    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "activated", nullable = false)
    @Getter
    @Setter
    @NotNull
    private int activated;
}
