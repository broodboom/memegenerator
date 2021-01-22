package com.example.memegenerator.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import com.example.memegenerator.web.dto.SocketResponseDto;

import java.util.NoSuchElementException;

import com.example.memegenerator.domain.service.MemeService;
import com.example.memegenerator.domain.service.UserService;
import com.example.memegenerator.web.dto.MemeDto;

@RestController
@RequestMapping("likedislike")
@RequiredArgsConstructor
public class LikeDislikeController {

    private final MemeService memeService;
    private final UserService userService;

    @MessageMapping("/")
    public ResponseEntity<SocketResponseDto> likedislike(@RequestBody SocketResponseDto response) {

        try {

            MemeDto memeDto = memeService.getMemeById(response.memeId);

            if (response.isUpvote) {

                memeDto.likes++;
                userService.updateUserPoints(response.userId, 1);
                userService.updateUserPoints(response.userId, 1);
            } else {

                memeDto.dislikes++;
                userService.updateUserPoints(response.userId, 1);
            }

            memeService.updateMeme(memeDto);

            return new ResponseEntity<SocketResponseDto>(response, HttpStatus.OK);

        } catch (NoSuchElementException e) {

            return new ResponseEntity<SocketResponseDto>(response, HttpStatus.NOT_FOUND);
        }
    }
}
