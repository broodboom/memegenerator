package com.example.memegenerator.service.impl;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.example.memegenerator.domain.Meme;
import com.example.memegenerator.domain.Tag;
import com.example.memegenerator.domain.User;
import com.example.memegenerator.repository.MemeRepository;
import com.example.memegenerator.repository.TagRepository;
import com.example.memegenerator.request.MemeModel;
import com.example.memegenerator.service.MemeService;
import com.example.memegenerator.service.UserService;
import com.example.memegenerator.shared.dto.MemeDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemeServiceImpl implements MemeService {

    @Autowired
    MemeRepository memeRepository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    UserService userService;

    public Meme createMeme(MemeDto meme, Long id) {
        if(!userAllowedToCreate(id)) return new Meme();

        Meme dbMeme = new Meme();
        dbMeme.title = meme.title;
        dbMeme.description = meme.description;
        dbMeme.imageblob = meme.imageblob;
        dbMeme.likes = meme.likes;
        dbMeme.dislikes = meme.dislikes;
        dbMeme.createdat = new Timestamp(System.currentTimeMillis());

        for (Tag element : meme.tags) {
            Optional<Tag> dbTag = tagRepository.findById(element.id);

            if(!dbTag.isPresent()) continue;

            dbMeme.tags.add(dbTag.get());
        }

        dbMeme.user = userService.getDbUserByUserId(id);

        return memeRepository.save(dbMeme);
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
        memeModel.user = meme.get().user;

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

    @Override
    public List<Meme> getMemes() {
        List<Meme> all = (List<Meme>) memeRepository.findAll();

        all.sort(Comparator.comparing(Meme::getCreatedat).reversed());

        return all;
    }

    @Override
    public Boolean userAllowedToCreate(Long userId) {
        boolean result = false;

        User user = userService.getUserById(userId);

        var currentDate = LocalDate.now();
        Integer memesAddedCount = memeRepository.countAddedRecordsTodayByUser(currentDate.toString(), userId);

        Integer userAmountToPost = 0;
        if(user.points < 100){
            userAmountToPost = 1;
        }else if(user.points < 500 ){
            userAmountToPost = 5;
        }
        else if(user.points < 1000 ){
            userAmountToPost = 10;
        }else{
            userAmountToPost = -1;
            result = true;
        }


        if(userAmountToPost != -1){
            result = userAmountToPost > memesAddedCount;
        }

        return result;
    }
}
