package com.example.assignment2.service;

import com.example.assignment2.request.MemeModel;
import com.example.assignment2.shared.dto.MemeDto;

public interface MemeService {
    void createMeme(MemeDto meme);
    MemeModel getMemeById (long id);

    void updateMeme(MemeDto meme);
}
