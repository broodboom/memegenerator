package com.example.memegenerator.web.dto;

import com.example.memegenerator.data.entity.Tag;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemeDto {

    public long id;

    public String title;

    public String description;

    public byte[] imageblob;

    public int likes;

    public int dislikes;

    public Tag[] tags;
}
