package com.dp.vvgram.dtos;

import com.dp.vvgram.models.Comment;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CommentDto {
    private String text;
    private String imageUrl = "";
    private String gifUrl = "";
    private String username;

    public static List<CommentDto> from (List<Comment> comment) {
        List<CommentDto> commentDtos = new ArrayList<>();
        for (Comment c : comment) {
            CommentDto commentDto = new CommentDto();
            commentDto.setText(c.getText());
            commentDto.setImageUrl(c.getImageUrl());
            commentDto.setGifUrl(c.getGifUrl());
            commentDto.setUsername(c.getUser().getUsername());
            commentDtos.add(commentDto);
        }
        return commentDtos;
    }
}
