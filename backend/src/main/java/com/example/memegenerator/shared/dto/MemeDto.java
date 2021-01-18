package com.example.memegenerator.shared.dto;

import com.example.memegenerator.domain.Tag;

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

    @Getter
    @Setter
    public Tag[] tags;

    @Getter
    @Setter
    public int categoryId;
}
