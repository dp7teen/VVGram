package com.dp.vvgram.services.commentEditor;

import com.dp.vvgram.dtos.CommentRequestDto;
import com.dp.vvgram.models.Comment;
import org.springframework.stereotype.Component;

@Component
public class TextEditor implements CommentEditor {
    @Override
    public void editComment(CommentRequestDto dto, Comment comment) {
        if (dto.getText() != null) {
            comment.setText(dto.getText());
        }
    }
}
