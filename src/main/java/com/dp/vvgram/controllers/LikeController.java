package com.dp.vvgram.controllers;

import com.dp.vvgram.dtos.LikeUserDto;
import com.dp.vvgram.exceptions.LikeNotFoundException;
import com.dp.vvgram.exceptions.PostNotFoundException;
import com.dp.vvgram.exceptions.UserNotFoundException;
import com.dp.vvgram.models.Like;
import com.dp.vvgram.services.LikeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/likes")
public class LikeController {
    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("/like/{postId}/{username}")
    public ResponseEntity<String> like(@PathVariable long postId,
                                       @PathVariable String username) throws PostNotFoundException, UserNotFoundException {
        return new ResponseEntity<>(
                likeService.like(postId, username),
                HttpStatus.CREATED
        );
    }

    @PostMapping("/unlike/{postId}/{username}")
    public ResponseEntity<String> unLike(@PathVariable long postId,
                                         @PathVariable String username) throws PostNotFoundException, UserNotFoundException, LikeNotFoundException {
        return new ResponseEntity<>(
                likeService.unLike(postId, username),
                HttpStatus.OK
        );
    }

    @GetMapping("/{postId}")
    public List<LikeUserDto> getLikes(@PathVariable long postId) throws PostNotFoundException {
        List<Like> likes = likeService.getLikes(postId);
        return LikeUserDto.from(likes);
    }

}
