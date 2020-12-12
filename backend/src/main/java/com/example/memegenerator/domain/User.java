package com.example.memegenerator.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.example.memegenerator.security.Role;

import java.util.HashSet;
import java.util.Set;

@Entity
public class User extends BaseEntity {

    /**
     *
     */
    private static final long serialVersionUID = 5184790245397751389L;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(name = "user_achievements", joinColumns = { @JoinColumn(name = "userid") }, inverseJoinColumns = {
            @JoinColumn(name = "achievementid") })
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

    @Column(name = "token")
    @Getter
    @Setter
    public String token;

    @Column(name = "confirmation_token")
    @Getter
    @Setter
    public String confirmationToken;

    @Column(name = "role", nullable = false)
    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    public Role role;

    @Column(name = "activated", nullable = false)
    @Getter
    @Setter
    @NotNull(message = "No activated give")
    public boolean activated;
}