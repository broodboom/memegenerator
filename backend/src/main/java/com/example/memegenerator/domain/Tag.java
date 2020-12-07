package com.example.memegenerator.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Tag extends BaseEntity {

    @Column(name = "title", nullable = false)
    @Getter
    @Setter
    @NotNull
    private String title;
}
