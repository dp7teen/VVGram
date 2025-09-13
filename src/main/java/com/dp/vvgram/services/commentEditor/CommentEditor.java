package com.dp.vvgram.services.commentEditor;

import com.dp.vvgram.dtos.CommentRequestDto;
import com.dp.vvgram.models.Comment;
import org.springframework.stereotype.Component;

@Component
public interface CommentEditor {
    void editComment(CommentRequestDto dto, Comment comment);
}
