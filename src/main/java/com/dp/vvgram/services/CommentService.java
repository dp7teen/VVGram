package com.dp.vvgram.services;

import com.dp.vvgram.dtos.CommentDto;
import com.dp.vvgram.dtos.CommentRequestDto;
import com.dp.vvgram.exceptions.CommentNotFoundException;
import com.dp.vvgram.exceptions.PostNotFoundException;
import com.dp.vvgram.exceptions.UserNotFoundException;
import com.dp.vvgram.models.Comment;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService extends CommentTypeService{

    String uncomment(long postId, long commentId) throws CommentNotFoundException,
            UserNotFoundException, PostNotFoundException;

    String editComment(long commentId, CommentRequestDto commentRequestDto) throws UserNotFoundException,
            CommentNotFoundException;

    Page<CommentDto> getComments(long postId, int pageNo, int pageSize, List<String> sortBy) throws PostNotFoundException;
}
