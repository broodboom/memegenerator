package com.example.assignment2.request;

import com.example.assignment2.domain.Image;
import com.example.assignment2.domain.Tag;
import com.example.assignment2.domain.UserEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Blob;
import java.util.HashSet;
import java.util.Set;

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
