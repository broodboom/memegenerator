package com.example.memegenerator.service.impl;

import java.util.Optional;

import com.example.memegenerator.domain.Meme;
import com.example.memegenerator.repository.MemeRepository;
import com.example.memegenerator.request.MemeModel;
import com.example.memegenerator.service.MemeService;
import com.example.memegenerator.shared.dto.MemeDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemeServiceImpl implements MemeService {

    @Autowired
    MemeRepository memeRepository;

    public void createMeme(MemeDto meme) {
        Meme dbMeme = new Meme();
        dbMeme.title = meme.title;
        dbMeme.description = meme.description;
        dbMeme.imageblob = dbMeme.imageblob;
        dbMeme.likes = meme.likes;
        dbMeme.dislikes = meme.dislikes;

        memeRepository.save(dbMeme);
    }

    @Override
    public MemeModel getMemeById(long id) {
        Optional<Meme> meme = memeRepository.findById(id);

        if(!meme.isPresent()) return new MemeModel();

        MemeModel memeModel = new MemeModel();
        memeModel.title = meme.get().title;
        memeModel.description = meme.get().description;
        memeModel.imageblob = meme.get().imageblob;
        memeModel.likes = meme.get().likes;
        memeModel.dislikes = meme.get().dislikes;

        return memeModel;
    }

    @Override
    public void updateMeme(MemeDto meme) {
        Optional<Meme> dbMeme = memeRepository.findById(meme.id);

        if(!dbMeme.isPresent()) return;

        Meme dbMemeTemp = dbMeme.get(); 

        dbMemeTemp.title = meme.title;
        dbMemeTemp.description = meme.description;
        dbMemeTemp.imageblob = meme.imageblob;
        dbMemeTemp.likes = meme.likes;
        dbMemeTemp.dislikes = meme.dislikes;

        memeRepository.save(dbMemeTemp);
    }
}
