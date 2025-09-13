package com.dp.vvgram.services.postEditor;

import com.dp.vvgram.dtos.PostRequestDto;
import com.dp.vvgram.models.Post;
import org.springframework.stereotype.Component;

@Component
public interface PostEditor {
    void edit(Post post, PostRequestDto postRequestDto);
}
