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
public class Image extends BaseEntity {

    @Column(name = "title", unique = true, nullable = false)
    @Getter
    @Setter
    @NotNull
    private String title;

    @Column(name = "image", unique = true, nullable = false)
    @Getter
    @Setter
    @NotNull
    private Blob imageblob;

    @ManyToOne
    @JoinColumn(name="userid", nullable=false)
    private User user;
}
