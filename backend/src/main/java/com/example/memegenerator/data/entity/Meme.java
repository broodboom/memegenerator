package com.example.memegenerator.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Meme extends BaseEntity {

    @Column(name = "title", nullable = false)
    @NotNull
    public String title;

    @Column(name = "description")
    public String description;

    @Column(name = "image", nullable = false)
    public byte[] imageblob;

    @Column(name = "likes")
    public int likes;

    @Column(name = "dislikes")
    public int dislikes;

    @ManyToOne
    @JoinColumn(name = "imageid")
    public Image image;

    @ManyToOne
    @JoinColumn(name = "userid", nullable = false)
    public User user;

    @ManyToOne
    @JoinColumn(name = "categoryid", nullable = false)
    public Category category;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(name = "meme_tags", joinColumns = { @JoinColumn(name = "memeid") }, inverseJoinColumns = {
            @JoinColumn(name = "tagid") })
    public Set<Tag> tags = new HashSet<>();
}