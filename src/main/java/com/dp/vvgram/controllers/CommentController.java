package com.dp.vvgram.controllers;

import com.dp.vvgram.exceptions.LikeNotFoundException;
import com.dp.vvgram.exceptions.PostNotFoundException;
import com.dp.vvgram.exceptions.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    public ResponseEntity<String> comment(@PathVariable long postId,
                                          @PathVariable String username) throws UserNotFoundException, PostNotFoundException {
        return null;
    }

    public ResponseEntity<String> unComment(@PathVariable long postId,
                                            @PathVariable String username) throws UserNotFoundException, PostNotFoundException {
        return null;
    }

    public ResponseEntity<String> editComment(@PathVariable long postId) throws UserNotFoundException, PostNotFoundException {
        return null;
    }
}
