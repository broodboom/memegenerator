package com.example.assignment2.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.sql.Blob;

@Entity
public class Image extends BaseEntity {

    /**
     *
     */
    private static final long serialVersionUID = 5366929109425794141L;

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
