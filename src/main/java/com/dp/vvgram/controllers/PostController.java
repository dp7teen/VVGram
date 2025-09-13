package com.dp.vvgram.controllers;

import com.dp.vvgram.dtos.PostDto;
import com.dp.vvgram.dtos.PostRequestDto;
import com.dp.vvgram.exceptions.PostNotFoundException;
import com.dp.vvgram.exceptions.PostingServiceNotAvailableException;
import com.dp.vvgram.exceptions.UserNotFoundException;
import com.dp.vvgram.models.Post;
import com.dp.vvgram.services.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/{username}")
    public ResponseEntity<String> uploadPost(@PathVariable String username,
                                             @RequestBody PostRequestDto requestDto) throws UserNotFoundException, PostingServiceNotAvailableException {
        String message = postService.upload(username, requestDto);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @PatchMapping("/edit/{username}/{postId}")
    public ResponseEntity<String> updatePost(@PathVariable String username,
                                             @PathVariable Long postId,
                                             @RequestBody PostRequestDto requestDto) throws UserNotFoundException, PostNotFoundException {
        return new ResponseEntity<>(
                postService.editPost(username, postId, requestDto),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("/delete/{username}/{id}")
    public ResponseEntity<String> deletePost(@PathVariable String username,
                                             @PathVariable Long id) throws UserNotFoundException, PostNotFoundException {
        return new ResponseEntity<>(
                postService.delete(username, id),
                HttpStatus.OK
        );
    }

    @GetMapping("/{username}")
    public List<PostDto> getPostsByUser(@PathVariable String username) throws UserNotFoundException {
        List<Post> posts = postService.getPostsByUser(username);
        return PostDto.from(posts);
    }

    @GetMapping("/{username}/{id}")
    public PostDto getPostById(@PathVariable String username,
                               @PathVariable long id) throws UserNotFoundException, PostNotFoundException {
        Post post = postService.getPostById(username, id);
        return PostDto.from(List.of(post)).getFirst();
    }
}
