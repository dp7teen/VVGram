package com.dp.vvgram.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDto {
    private String text;
    private String imageUrl;
    private String gifUrl;
}
