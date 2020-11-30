package com.example.assignment2.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Tag extends BaseEntity {

    @Column(name = "title", nullable = false)
    @Getter
    @Setter
    @NotNull
    private String title;
}
