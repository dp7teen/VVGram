package com.dp.vvgram.controllers;

import com.dp.vvgram.dtos.LikeUserDto;
import com.dp.vvgram.dtos.ShowLikesDto;
import com.dp.vvgram.exceptions.LikeNotFoundException;
import com.dp.vvgram.exceptions.PostNotFoundException;
import com.dp.vvgram.exceptions.UserNotFoundException;
import com.dp.vvgram.models.Like;
import com.dp.vvgram.services.LikeService;
import com.dp.vvgram.utilities.PrincipalHelper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/likes")
public class LikeController {
    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("/{postId}")
    public ResponseEntity<String> like(@PathVariable long postId) throws PostNotFoundException, UserNotFoundException {
        UserDetails details = PrincipalHelper.getPrincipal();
        return new ResponseEntity<>(
                likeService.like(postId, details.getUsername()),
                HttpStatus.CREATED
        );
    }

    @PostMapping("/unlike/{postId}")
    public ResponseEntity<String> unLike(@PathVariable long postId) throws PostNotFoundException, UserNotFoundException, LikeNotFoundException {
        UserDetails details = PrincipalHelper.getPrincipal();
        return new ResponseEntity<>(
                likeService.unLike(postId, details.getUsername()),
                HttpStatus.OK
        );
    }

    @PostMapping
    public Page<LikeUserDto> getLikes(@RequestBody ShowLikesDto dto) throws PostNotFoundException {
        return likeService.getLikes(dto.getPostid(),
                dto.getPageno(), dto.getPagesize(), dto.getSortBy());
    }

}
