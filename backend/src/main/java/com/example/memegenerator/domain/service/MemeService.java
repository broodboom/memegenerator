package com.example.memegenerator.domain.service;

import java.util.List;
import java.util.NoSuchElementException;

import com.example.memegenerator.web.dto.MemeDto;

public interface MemeService {
    
    MemeDto createMeme(MemeDto meme, Long id) throws NoSuchElementException;

    MemeDto getMemeById(long id) throws NoSuchElementException;

    MemeDto updateMeme(MemeDto meme) throws NoSuchElementException;

    List<MemeDto> getMemes();

    List<MemeDto> getFilteredMemes(long filter) throws NoSuchElementException;
    List<MemeDto> getFilteredMemesTag(List<Long> tagIds);
    boolean userAllowedToCreate(Long userId) throws NoSuchElementException;
}
