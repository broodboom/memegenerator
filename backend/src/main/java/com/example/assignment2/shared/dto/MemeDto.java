package com.example.assignment2.shared.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Blob;

public class MemeDto {
    @Getter
    @Setter

    public Long id;

    @Getter
    @Setter

    public String title;


    @Getter
    @Setter
    public String description;


    @Getter
    @Setter
    public Blob imageblob;


    @Getter
    @Setter
    public int likes;


    @Getter
    @Setter
    public int dislikes;
}
