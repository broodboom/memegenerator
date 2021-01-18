package com.example.memegenerator.domain.service;

import java.util.List;

import com.example.memegenerator.data.entity.Meme;
import com.example.memegenerator.web.dto.MemeDto;

public interface MemeService {
    
    Meme createMeme(MemeDto meme, Long id);

    MemeDto getMemeById(long id);

    void updateMeme(MemeDto meme);

    List<Meme> getMemes();

    List<Meme> getFilteredMemes(long filter);
}
