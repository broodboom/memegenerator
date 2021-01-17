package com.example.memegenerator.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
public class Category extends BaseEntity {

    @Column(name = "name", nullable = false)
    @NotNull
    public String name;
}