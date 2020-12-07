package com.example.memegenerator.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.sql.Blob;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Meme extends BaseEntity {
    
    /**
     *
     */
    private static final long serialVersionUID = 4756717328435193553L;

    @Column(name = "title", nullable = false)
    @Getter
    @Setter
    @NotNull
    private String title;

    @Column(name = "description")
    @Getter
    @Setter
    private String description;

    @Column(name = "image", nullable = false)
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
    @JoinColumn(name = "imageid", nullable = false)
    private Image image;

    @ManyToOne
    @JoinColumn(name = "userid", nullable = false)
    private UserEntity user;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
        name = "meme_tags", 
        joinColumns = { @JoinColumn(name = "memeid") }, 
        inverseJoinColumns = { @JoinColumn(name = "tagid") }
    )
    private Set<Tag> tags = new HashSet<>();
}