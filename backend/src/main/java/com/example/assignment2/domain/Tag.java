package com.example.assignment2.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Tag extends BaseEntity {

    /**
     *
     */
    private static final long serialVersionUID = -6249224032852761005L;
    
    @Column(name = "title", nullable = false)
    @Getter
    @Setter
    @NotNull
    private String title;
}
