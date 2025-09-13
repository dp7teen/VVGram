package com.dp.vvgram.services;

import com.dp.vvgram.exceptions.CommentNotFoundException;
import com.dp.vvgram.exceptions.PostNotFoundException;
import com.dp.vvgram.exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;

@Service
public interface CommentService {
    String comment(long postId, String username) throws PostNotFoundException, UserNotFoundException;

    String uncomment(long postId, String username) throws CommentNotFoundException, UserNotFoundException, PostNotFoundException;

    String editComment(long postId);
}
