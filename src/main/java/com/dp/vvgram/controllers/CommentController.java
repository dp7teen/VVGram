package com.dp.vvgram.controllers;

import com.dp.vvgram.dtos.CommentDto;
import com.dp.vvgram.dtos.CommentRequestDto;
import com.dp.vvgram.dtos.ShowCommentsDto;
import com.dp.vvgram.exceptions.CommentNotFoundException;
import com.dp.vvgram.exceptions.PostNotFoundException;
import com.dp.vvgram.exceptions.UserNotFoundException;
import com.dp.vvgram.models.Comment;
import com.dp.vvgram.services.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{postId}/{username}")
    public ResponseEntity<String> comment(@PathVariable long postId,
                                          @PathVariable String username,
                                          @RequestBody CommentRequestDto dto) throws UserNotFoundException,
            PostNotFoundException {
        return new ResponseEntity<>(
              commentService.comment(postId, username, dto),
              HttpStatus.CREATED
        );
    }

    @DeleteMapping("/{postId}/{commentId}")
    public ResponseEntity<String> unComment(@PathVariable long postId,
                                            @PathVariable long commentId) throws UserNotFoundException, PostNotFoundException, CommentNotFoundException {
        return new ResponseEntity<>(
                commentService.uncomment(postId, commentId),
                HttpStatus.OK
        );
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<String> editComment(@PathVariable long commentId,
                                              @RequestBody CommentRequestDto dto) throws UserNotFoundException, CommentNotFoundException {
        return new ResponseEntity<>(
                commentService.editComment(commentId, dto),
                HttpStatus.CREATED
        );
    }

    @PostMapping
    public Page<CommentDto> getComments(@RequestBody ShowCommentsDto dto) throws PostNotFoundException {
        return (Page<CommentDto>) commentService.getComments(dto.getPostid(),
                dto.getPageno(), dto.getPagesize(), dto.getSortBy());
    }
}
