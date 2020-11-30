package com.example.assignment2.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
public class UserEntity extends BaseEntity {
    
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

    @OneToMany(mappedBy = "user")
    Set<Meme> memes = new HashSet<>();
    
    @Column(name = "username", nullable = false)
    @Getter
    @Setter
    @NotNull(message = "No username given")
    public String username;

    @Column(name = "password", nullable = false)
    @Getter
    @Setter
    @NotNull(message = "No password given")
	public String password;

    @Column(name = "points", nullable = true)
    @Getter
    @Setter
    public int points;

    @Column(name = "email", nullable = false)
    @Getter
    @Setter
    @NotNull(message = "No email given")
    public String email;

    @Column(name = "role", nullable = false)
    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    public Role role;

    @Column(name = "activated", nullable = false)
    @Getter
    @Setter
    @NotNull(message ="No activated give")
    public int activated;
}
