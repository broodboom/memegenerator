package com.example.memegenerator.request;

import lombok.Getter;
import lombok.Setter;

public class MemeModel {

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
}
