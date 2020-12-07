package com.example.assignment2.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.sql.Blob;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Meme extends BaseEntity {
    
    @Column(name = "title", nullable = false)
    @Getter
    @Setter
    @NotNull
    public String title;

    @Column(name = "description")
    @Getter
    @Setter
    public String description;

    @Column(name = "image", nullable = false)
    @Getter
    @Setter
    public Blob imageblob;

    @Column(name = "likes")
    @Getter
    @Setter
    public int likes;

    @Column(name = "dislikes")
    @Getter
    @Setter
    public int dislikes;

    @ManyToOne
    @JoinColumn(name = "imageid", nullable = false)
    public Image image;

    @ManyToOne
    @JoinColumn(name = "userid", nullable = false)
    public UserEntity user;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
        name = "meme_tags", 
        joinColumns = { @JoinColumn(name = "memeid") }, 
        inverseJoinColumns = { @JoinColumn(name = "tagid") }
    )
    public Set<Tag> tags = new HashSet<>();
}
