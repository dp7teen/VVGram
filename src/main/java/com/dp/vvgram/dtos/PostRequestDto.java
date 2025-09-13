package com.dp.vvgram.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostRequestDto {
    private String content;
    private String imageUrl;
    private String videoUrl;
}
