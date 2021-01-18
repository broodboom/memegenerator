package com.example.memegenerator.domain.service.impl;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import com.example.memegenerator.data.entity.Category;
import com.example.memegenerator.data.entity.Meme;
import com.example.memegenerator.data.repository.CategoryRepository;
import com.example.memegenerator.data.repository.MemeRepository;
import com.example.memegenerator.domain.service.MemeService;
import com.example.memegenerator.data.repository.TagRepository;
import com.example.memegenerator.data.repository.UserRepository;
import com.example.memegenerator.web.dto.MemeDto;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemeServiceImpl implements MemeService {

    @Autowired
    final MemeRepository memeRepository;

    @Autowired
    final TagRepository tagRepository;

    @Autowired
    final CategoryRepository categoryRepository;

    @Autowired
    final UserRepository userRepository;

    @Autowired
    final ModelMapper modelMapper;

    public Meme createMeme(MemeDto memeDto, Long userId) throws NoSuchElementException {

        Meme meme = modelMapper.map(memeDto, Meme.class);

        meme.user = userRepository.findById(userId).orElseThrow();

        return memeRepository.save(meme);
    }

    @Override
    public MemeDto getMemeById(long memeId) throws NoSuchElementException {

        Meme meme = memeRepository.findById(memeId).orElseThrow();

        return modelMapper.map(meme, MemeDto.class);
    }

    @Override
    public void updateMeme(MemeDto memeDto) throws NoSuchElementException {

        Meme meme = memeRepository.findById(memeDto.id).orElseThrow();

        modelMapper.map(meme, memeDto);

        memeRepository.save(meme);
    }

    @Override
    public List<Meme> getMemes() {

        List<Meme> allMemes = memeRepository.findAll();

        allMemes.sort(Comparator.comparing(Meme::getCreatedat).reversed());

        return allMemes;
    }

    @Override
    public List<Meme> getFilteredMemes(long categoryId) throws NoSuchElementException {

        List<Meme> allMemes = memeRepository.findAll();

        Category category = categoryRepository.findById(categoryId).orElseThrow();

        allMemes.sort(Comparator.comparing(Meme::getCreatedat).reversed());

        return allMemes.stream().filter(t -> t.category != null).filter(t -> t.category.id.equals(category.id))
                .collect(Collectors.toList());
    }
}
