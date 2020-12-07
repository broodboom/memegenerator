package com.example.memegenerator.service;

import com.example.memegenerator.request.MemeModel;
import com.example.memegenerator.shared.dto.MemeDto;

public interface MemeService {
    void createMeme(MemeDto meme);

    MemeModel getMemeById(long id);

    void updateMeme(MemeDto meme);
}
