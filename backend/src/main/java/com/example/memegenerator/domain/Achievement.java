package com.example.memegenerator.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Achievement extends BaseEntity {

    /**
     *
     */
    private static final long serialVersionUID = -5620579023438427816L;

    @Column(name = "name", nullable = false)
    @Getter
    @Setter
    @NotNull
    public String title;

    @ManyToMany(mappedBy = "achievements")
    public Set<User> users = new HashSet<>();
}