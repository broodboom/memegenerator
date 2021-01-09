package com.example.memegenerator.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Category extends BaseEntity {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Column(name = "name", nullable = false)
    @Getter
    @Setter
    @NotNull
    public String name;
}