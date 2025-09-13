package com.dp.vvgram.services;

import com.dp.vvgram.dtos.CommentRequestDto;
import com.dp.vvgram.exceptions.PostNotFoundException;
import com.dp.vvgram.exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;

@Service
public interface CommentTypeService {
    String comment(long postId, String username, CommentRequestDto commentRequestDto) throws UserNotFoundException, PostNotFoundException;
}
