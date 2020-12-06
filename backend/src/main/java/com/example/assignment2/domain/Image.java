package com.example.assignment2.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.sql.Blob;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Image extends BaseEntity {

    @OneToMany(mappedBy = "image")
    Set<Meme> memes = new HashSet<>();

    @Column(name = "title", nullable = false)
    @Getter
    @Setter
    @NotNull
    private String title;

    @Column(name = "image", nullable = false)
    @Getter
    @Setter
    @NotNull
    private Blob imageblob;

    @ManyToOne
    @JoinColumn(name = "userid", nullable = false)
    private UserEntity user;
}
