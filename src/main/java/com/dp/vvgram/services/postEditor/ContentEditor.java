package com.dp.vvgram.services.postEditor;

import com.dp.vvgram.dtos.PostRequestDto;
import com.dp.vvgram.models.Post;
import org.springframework.stereotype.Component;

@Component
public class ContentEditor implements PostEditor {
    @Override
    public void edit(Post post, PostRequestDto postRequestDto) {
        if (postRequestDto.getContent() != null) {
            post.setContent(postRequestDto.getContent());
        }
    }
}
