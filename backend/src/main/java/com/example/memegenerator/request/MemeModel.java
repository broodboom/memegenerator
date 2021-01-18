package com.example.memegenerator.request;

import com.example.memegenerator.domain.Tag;
import com.example.memegenerator.domain.User;

import lombok.Getter;
import lombok.Setter;

public class MemeModel {

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
    public byte[] imageblob;

    @Getter
    @Setter
    public int likes;

    @Getter
    @Setter
    public int dislikes;

    @Getter
    @Setter
    public Tag[] tags;

    @Getter
    @Setter
    public User user;

    @Getter
    @Setter
    public long categoryId;
}
