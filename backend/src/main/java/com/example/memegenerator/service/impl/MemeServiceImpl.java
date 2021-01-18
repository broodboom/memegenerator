package com.example.memegenerator.service.impl;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import com.example.memegenerator.domain.Meme;
import com.example.memegenerator.domain.Tag;
import com.example.memegenerator.domain.User;
import com.example.memegenerator.repository.MemeRepository;
import com.example.memegenerator.repository.TagRepository;
import com.example.memegenerator.request.MemeModel;
import com.example.memegenerator.service.MemeService;
import com.example.memegenerator.service.UserService;
import com.example.memegenerator.shared.dto.MemeDto;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.awt.Graphics2D;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;

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
        if (!userAllowedToCreate(id))
            return new Meme();

        Meme dbMeme = new Meme();
        dbMeme.title = meme.title;
        dbMeme.description = meme.description;
        dbMeme.imageblob = meme.imageblob;
        dbMeme.likes = meme.likes;
        dbMeme.dislikes = meme.dislikes;
        dbMeme.createdat = new Timestamp(System.currentTimeMillis());

        dbMeme.user = userService.getDbUserByUserId(id);

        if(dbMeme.user.points >=1000){
            // Convert byte[] to BufferedImage
            BufferedImage bufferedImage = this.createImageFromBytes(dbMeme.imageblob);

            // Create watermarked image
            BufferedImage bufferedImageWithWatermark = this.addTextWatermark("PREMIUM MEME", bufferedImage);

            // Convert BufferedImage to byte[]
            byte[] watermarkedMeme = this.createBytesFromImage(bufferedImageWithWatermark);

            dbMeme.imageblob = watermarkedMeme;
        }

        for (Tag element : meme.tags) {
            Optional<Tag> dbTag = tagRepository.findById(element.id);

            if (!dbTag.isPresent())
                continue;

            dbMeme.tags.add(dbTag.get());
        }

        return memeRepository.save(dbMeme);
    }

    @Override
    public MemeModel getMemeById(long id) {
        Optional<Meme> meme = memeRepository.findById(id);

        if (!meme.isPresent())
            return new MemeModel();

        MemeModel memeModel = new MemeModel();
        memeModel.title = meme.get().title;
        memeModel.description = meme.get().description;
        memeModel.imageblob = meme.get().imageblob;
        memeModel.likes = meme.get().likes;
        memeModel.dislikes = meme.get().dislikes;
        memeModel.user = meme.get().user;
        memeModel.categoryId = meme.get().category.id;

        return memeModel;
    }

    @Override
    public void updateMeme(MemeDto meme) {
        Optional<Meme> dbMeme = memeRepository.findById(meme.id);

        if (!dbMeme.isPresent())
            return;

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
    public List<Meme> getFilteredMemes(long filter) {
        List<Meme> all = (List<Meme>) memeRepository.findAll();
        all.sort(Comparator.comparing(Meme::getCreatedat).reversed());
        List<Meme> filtered = all.stream()
                .filter( t-> t.category != null)
                .filter(t -> t.category.id.equals(filter))
                .collect(Collectors.toList());
        return filtered;
    }

    public Boolean userAllowedToCreate(Long userId) {
        boolean result = false;

        User user = userService.getUserById(userId);

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


        if(userAmountToPost != -1){
            result = userAmountToPost > memesAddedCount;
        }

        return result;
    }

    private BufferedImage createImageFromBytes(byte[] imageData) {
        ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
        try {
            return ImageIO.read(bais);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private BufferedImage addTextWatermark(String text, BufferedImage sourceImage) {
        Graphics2D g2d = (Graphics2D) sourceImage.getGraphics();

        // initializes necessary graphic properties
        AlphaComposite alphaChannel = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f);
        g2d.setComposite(alphaChannel);
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 64));
        FontMetrics fontMetrics = g2d.getFontMetrics();
        java.awt.geom.Rectangle2D rect = fontMetrics.getStringBounds(text, g2d);

        // calculates the coordinate where the String is painted
        int centerX = (sourceImage.getWidth() - (int) rect.getWidth()) / 2;
        int centerY = sourceImage.getHeight() / 2;

        // paints the textual watermark
        g2d.drawString(text, centerX, centerY);

        return sourceImage;
    }

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
