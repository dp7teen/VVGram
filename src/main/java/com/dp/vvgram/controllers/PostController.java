package com.dp.vvgram.controllers;

import com.dp.vvgram.dtos.GetPostsDto;
import com.dp.vvgram.dtos.PostDto;
import com.dp.vvgram.dtos.PostRequestDto;
import com.dp.vvgram.exceptions.PostNotFoundException;
import com.dp.vvgram.exceptions.PostingServiceNotAvailableException;
import com.dp.vvgram.exceptions.UserNotFoundException;
import com.dp.vvgram.models.Post;
import com.dp.vvgram.services.PostService;
import com.dp.vvgram.utilities.PrincipalHelper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class    PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<String> uploadPost(@RequestBody PostRequestDto requestDto) throws UserNotFoundException, PostingServiceNotAvailableException {
        UserDetails details = PrincipalHelper.getPrincipal();
        String message = postService.upload(details.getUsername(), requestDto);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<String> updatePost(@PathVariable Long postId,
                                             @RequestBody PostRequestDto requestDto) throws UserNotFoundException, PostNotFoundException {
        UserDetails details = PrincipalHelper.getPrincipal();
        return new ResponseEntity<>(
                postService.editPost(details.getUsername(), postId, requestDto),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id) throws UserNotFoundException, PostNotFoundException {
        UserDetails details = PrincipalHelper.getPrincipal();
        return new ResponseEntity<>(
                postService.delete(details.getUsername(), id),
                HttpStatus.OK
        );
    }

    @GetMapping
    public Page<PostDto> getPostsByUser(@RequestBody GetPostsDto getPostsDto) throws UserNotFoundException {
        return postService.getPostsByUser(getPostsDto.getUsername(),
                getPostsDto.getPageno(), getPostsDto.getPagesize(), getPostsDto.getSortBy());
    }
}
