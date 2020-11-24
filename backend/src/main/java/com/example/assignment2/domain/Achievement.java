package com.example.assignment2.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import java.sql.Blob;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Achievement extends BaseEntity {

    @Column(name = "name", unique = true, nullable = false)
    @Getter
    @Setter
    @NotNull
    private String title;

    @ManyToMany(mappedBy = "achievements")
    private Set<User> users = new HashSet<>();
}
