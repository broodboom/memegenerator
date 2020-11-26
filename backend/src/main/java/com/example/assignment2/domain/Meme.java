package com.example.assignment2.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.sql.Blob;

@Entity
public class Meme extends BaseEntity {

    /**
     *
     */
    private static final long serialVersionUID = 6179148044106686855L;

    @Column(name = "title", unique = true, nullable = false)
    @Getter
    @Setter
    @NotNull
    private String title;

    @Column(name = "description", unique = true, nullable = true)
    @Getter
    @Setter
    private String description;

    @Column(name = "image", unique = true, nullable = false)
    @Getter
    @Setter
    @NotNull
    private Blob imageblob;

    @Column(name = "likes", unique = true, nullable = false)
    @Getter
    @Setter
    @NotNull
    private int likes;

    @Column(name = "dislikes", unique = true, nullable = false)
    @Getter
    @Setter
    @NotNull
    private int dislikes;

    @ManyToOne
    @JoinColumn(name="imageid", nullable=false)
    private Image image;

    @ManyToOne
    @JoinColumn(name="userid", nullable=false)
    private User user;
}
