package com.example.memegenerator.domain.service.impl;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import com.example.memegenerator.data.entity.Category;
import com.example.memegenerator.data.entity.Meme;
import com.example.memegenerator.data.entity.User;
import com.example.memegenerator.data.repository.CategoryRepository;
import com.example.memegenerator.data.repository.MemeRepository;
import com.example.memegenerator.domain.service.MemeService;
import com.example.memegenerator.data.repository.UserRepository;
import com.example.memegenerator.web.dto.MemeDto;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemeServiceImpl implements MemeService {

    private final MemeRepository memeRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public MemeDto createMeme(MemeDto memeDto, Long userId) throws NoSuchElementException {

        Meme meme = modelMapper.map(memeDto, Meme.class);

        meme.user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));

        Meme savedMeme = memeRepository.save(meme);

        return modelMapper.map(savedMeme, MemeDto.class);
    }

    public MemeDto getMemeById(long memeId) throws NoSuchElementException {

        Meme meme = memeRepository.findById(memeId).orElseThrow(() -> new NoSuchElementException("Meme not found"));

        return modelMapper.map(meme, MemeDto.class);
    }

    public MemeDto updateMeme(MemeDto memeDto) throws NoSuchElementException {

        Meme meme = memeRepository.findById(memeDto.id).orElseThrow(() -> new NoSuchElementException("Meme not found"));

        modelMapper.map(meme, memeDto);

        Meme savedMeme = memeRepository.save(meme);

        return modelMapper.map(savedMeme, MemeDto.class);
    }

    public List<MemeDto> getMemes() {

        List<Meme> allMemes = memeRepository.findAll();

        allMemes.sort(Comparator.comparing(Meme::getCreatedat).reversed());

        return allMemes.stream().map(meme -> modelMapper.map(meme, MemeDto.class)).collect(Collectors.toList());
    }

    public List<MemeDto> getFilteredMemes(long categoryId) throws NoSuchElementException {

        List<Meme> allMemes = memeRepository.findAll();

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NoSuchElementException("Category not found"));

        allMemes.sort(Comparator.comparing(Meme::getCreatedat).reversed());

        List<Meme> filteredMemes = allMemes.stream().filter(t -> t.category != null)
                .filter(t -> t.category.id.equals(category.id)).collect(Collectors.toList());

        return filteredMemes.stream().map(meme -> modelMapper.map(meme, MemeDto.class)).collect(Collectors.toList());
    }

    @Override
    public Boolean userAllowedToCreate(Long userId) throws NoSuchElementException {

        boolean result = false;

        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));

        var currentDate = LocalDate.now();
        Integer memesAddedCount = memeRepository.countAddedRecordsTodayByUser(currentDate.toString(), userId);

        Integer userAmountToPost = 0;
        if (user.points < 100) {
            userAmountToPost = 1;
        } else if (user.points < 500) {
            userAmountToPost = 5;
        } else if (user.points < 1000) {
            userAmountToPost = 10;
        } else {
            userAmountToPost = -1;
            result = true;
        }

        if (userAmountToPost != -1) {
            result = userAmountToPost < memesAddedCount;
        }

        return result;
    }
}
