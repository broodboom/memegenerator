package com.example.assignment2.request;
import lombok.Getter;
import lombok.Setter;
import java.sql.Blob;

public class MemeModel {

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
