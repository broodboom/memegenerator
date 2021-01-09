package com.example.memegenerator.shared.dto;

import lombok.Getter;
import lombok.Setter;

public class MemeDto {
    @Getter
    @Setter

    public long id;

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
