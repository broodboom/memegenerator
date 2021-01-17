package com.example.memegenerator.domain.service.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import com.example.memegenerator.data.entity.Meme;
import com.example.memegenerator.data.repository.MemeRepository;
import com.example.memegenerator.domain.service.MemeService;
import com.example.memegenerator.domain.service.UserService;
import com.example.memegenerator.web.dto.MemeDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemeServiceImpl implements MemeService {

    @Autowired
    MemeRepository memeRepository;

    @Autowired
    UserService userService;

    public void createMeme(MemeDto meme, Long id) {

        Meme dbMeme = new Meme();
        dbMeme.title = meme.title;
        dbMeme.description = meme.description;
        dbMeme.imageblob = meme.imageblob;
        dbMeme.likes = meme.likes;
        dbMeme.dislikes = meme.dislikes;
        dbMeme.createdat = new Timestamp(System.currentTimeMillis());

        dbMeme.user = userService.getDbUserByUserId(id);

        memeRepository.save(dbMeme);
    }

    @Override
    public MemeDto getMemeById(long id) {

        Optional<Meme> dbMeme = memeRepository.findById(id);

        if (!dbMeme.isPresent()) {
            return new MemeDto();
        }

        MemeDto memeModel = new MemeDto();
        memeModel.id = dbMeme.get().id;
        memeModel.title = dbMeme.get().title;
        memeModel.description = dbMeme.get().description;
        memeModel.imageblob = dbMeme.get().imageblob;
        memeModel.likes = dbMeme.get().likes;
        memeModel.dislikes = dbMeme.get().dislikes;

        return memeModel;
    }

    @Override
    public void updateMeme(MemeDto meme) {

        Optional<Meme> dbMeme = memeRepository.findById(meme.id);

        if (!dbMeme.isPresent()) {
            return;
        }

        Meme dbMemeTemp = dbMeme.get();

        dbMemeTemp.title = meme.title;
        dbMemeTemp.description = meme.description;
        dbMemeTemp.imageblob = meme.imageblob;
        dbMemeTemp.likes = meme.likes;
        dbMemeTemp.dislikes = meme.dislikes;

        memeRepository.save(dbMemeTemp);
    }

    @Override
    public List<Meme> getMemes() {

        return (List<Meme>) memeRepository.findAll();
    }
}
