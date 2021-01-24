package com.example.memegenerator.domain.service.impl;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import com.example.memegenerator.data.entity.Category;
import com.example.memegenerator.data.entity.Meme;
import com.example.memegenerator.data.entity.User;
import com.example.memegenerator.data.repository.CategoryRepository;
import com.example.memegenerator.data.repository.MemeRepository;
import com.example.memegenerator.domain.service.MemeService;
import com.example.memegenerator.data.repository.UserRepository;
import com.example.memegenerator.web.dto.MemeDto;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.awt.Graphics2D;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;

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

    
    /** 
     * @param memeDto
     * @param userId
     * @return MemeDto
     * @throws NoSuchElementException
     */
    public MemeDto createMeme(MemeDto memeDto, Long userId) throws NoSuchElementException {

        Meme meme = modelMapper.map(memeDto, Meme.class);

        meme.user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));

        if (meme.user.points >= 1000) {

            BufferedImage bufferedImage = createImageFromBytes(meme.imageblob);
            BufferedImage bufferedImageWithWatermark = addTextWatermark("PREMIUM MEME", bufferedImage);
            byte[] watermarkedMeme = createBytesFromImage(bufferedImageWithWatermark);
            meme.imageblob = watermarkedMeme;
        }

        Meme savedMeme = memeRepository.save(meme);

        return modelMapper.map(savedMeme, MemeDto.class);
    }

    
    /** 
     * @param memeId
     * @return MemeDto
     * @throws NoSuchElementException
     */
    public MemeDto getMemeById(long memeId) throws NoSuchElementException {

        Meme meme = memeRepository.findById(memeId).orElseThrow(() -> new NoSuchElementException("Meme not found"));

        return modelMapper.map(meme, MemeDto.class);
    }

    
    /** 
     * @param memeDto
     * @return MemeDto
     * @throws NoSuchElementException
     */
    public MemeDto updateMeme(MemeDto memeDto) throws NoSuchElementException {

        Meme meme = memeRepository.findById(memeDto.id).orElseThrow(() -> new NoSuchElementException("Meme not found"));

        modelMapper.map(memeDto, meme);

        Meme savedMeme = memeRepository.save(meme);

        return modelMapper.map(savedMeme, MemeDto.class);
    }

    
    /** 
     * @return List<MemeDto>
     */
    public List<MemeDto> getMemes() {

        List<Meme> allMemes = memeRepository.findAll();

        allMemes.sort(Comparator.comparing(Meme::getCreatedat).reversed());

        return allMemes.stream().map(meme -> modelMapper.map(meme, MemeDto.class)).collect(Collectors.toList());
    }

    
    /** 
     * @param categoryId
     * @return List<MemeDto>
     * @throws NoSuchElementException
     */
    public List<MemeDto> getFilteredMemes(long categoryId) throws NoSuchElementException {

        List<Meme> allMemes = memeRepository.findAll();

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NoSuchElementException("Category not found"));

        allMemes.sort(Comparator.comparing(Meme::getCreatedat).reversed());

        List<Meme> filteredMemes = allMemes.stream().filter(t -> t.category != null)
                .filter(t -> t.category.id.equals(category.id)).collect(Collectors.toList());

        return filteredMemes.stream().map(meme -> modelMapper.map(meme, MemeDto.class)).collect(Collectors.toList());
    }

    
    /** 
     * @param userId
     * @return boolean
     * @throws NoSuchElementException
     */
    @Override
    public boolean userAllowedToCreate(Long userId) throws NoSuchElementException {

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
            result = userAmountToPost > memesAddedCount;
        }

        return result;
    }

    
    /** 
     * @param imageData
     * @return BufferedImage
     */
    private BufferedImage createImageFromBytes(byte[] imageData) {

        ByteArrayInputStream bais = new ByteArrayInputStream(imageData);

        try {

            return ImageIO.read(bais);
        } catch (IOException e) {

            throw new RuntimeException(e);
        }
    }

    
    /** 
     * @param text
     * @param sourceImage
     * @return BufferedImage
     */
    private BufferedImage addTextWatermark(String text, BufferedImage sourceImage) {

        Graphics2D g2d = (Graphics2D) sourceImage.getGraphics();

        AlphaComposite alphaChannel = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f);
        g2d.setComposite(alphaChannel);
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 64));
        FontMetrics fontMetrics = g2d.getFontMetrics();
        java.awt.geom.Rectangle2D rect = fontMetrics.getStringBounds(text, g2d);

        int centerX = (sourceImage.getWidth() - (int) rect.getWidth()) / 2;
        int centerY = sourceImage.getHeight() / 2;

        g2d.drawString(text, centerX, centerY);

        return sourceImage;
    }

    
    /** 
     * @param image
     * @return byte[]
     */
    private byte[] createBytesFromImage(BufferedImage image) {

        try {

            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            ImageIO.write(image, "png", baos);

            byte[] imageBytes = baos.toByteArray();
            baos.close();
            return imageBytes;

        } catch (IOException e) {

            throw new RuntimeException(e);
        }
    }
}
