package com.example.memegenerator.service;

import java.util.List;

import com.example.memegenerator.domain.Meme;
import com.example.memegenerator.request.MemeModel;
import com.example.memegenerator.shared.dto.MemeDto;

public interface MemeService {
    Meme createMeme(MemeDto meme, Long id);

    MemeModel getMemeById(long id);

    void updateMeme(MemeDto meme);

    List<Meme> getMemes();
}
