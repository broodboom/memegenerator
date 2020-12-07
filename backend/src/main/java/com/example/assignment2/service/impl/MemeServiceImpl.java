package com.example.assignment2.service.impl;

import com.example.assignment2.domain.Meme;
import com.example.assignment2.service.MemeService;
import com.example.assignment2.shared.dto.MemeDto;
import com.example.assignment2.respository.MemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemeServiceImpl implements MemeService {

    @Autowired
    MemeRepository memeRepository;

    public void CreateMeme(MemeDto meme){
        Meme dbMeme = new Meme();
        dbMeme.title = meme.title;
        dbMeme.description = meme.description;
        dbMeme.imageblob = dbMeme.imageblob;
        dbMeme.likes = meme.likes;
        dbMeme.dislikes = meme.dislikes;

        memeRepository.save(dbMeme);
    }
}
