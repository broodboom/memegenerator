package com.example.assignment2.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.sql.Blob;

@Entity
public class Meme extends BaseEntity {

    @Column(name = "title", nullable = false)
    @Getter
    @Setter
    @NotNull
    private String title;

    @Column(name = "description")
    @Getter
    @Setter
    private String description;

    @Column(name = "image")
    @Getter
    @Setter
    private Blob imageblob;

    @Column(name = "likes")
    @Getter
    @Setter
    private int likes;

    @Column(name = "dislikes")
    @Getter
    @Setter
    private int dislikes;

    @ManyToOne
    @JoinColumn(name = "imageid")
    private Image image;

    @ManyToOne
    @JoinColumn(name = "userid", nullable = false)
    private UserEntity user;
}
