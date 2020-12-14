package com.example.memegenerator.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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
    public String title;

    @Column(name = "description")
    @Getter
    @Setter
    public String description;

    @Column(name = "image", nullable = false)
    @Getter
    @Setter
    public byte[] imageblob;

    @Column(name = "likes")
    @Getter
    @Setter
    public int likes;

    @Column(name = "dislikes")
    @Getter
    @Setter
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