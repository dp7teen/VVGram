package com.dp.vvgram.factory;

import com.dp.vvgram.dtos.PostRequestDto;
import com.dp.vvgram.services.PostUploadService;
import com.dp.vvgram.strategies.postUploadStrategy.UploadOnlyTextStrategy;
import org.springframework.stereotype.Component;

@Component
public class PostUploadFactory {
    private static UploadOnlyTextStrategy uploadOnlyTextStrategy;

    public PostUploadFactory(UploadOnlyTextStrategy uploadOnlyTextStrategy) {
        PostUploadFactory.uploadOnlyTextStrategy = uploadOnlyTextStrategy;
    }

    public static PostUploadService getPostUploadingService(PostRequestDto postRequestDto) {
        if (postRequestDto.getContent() != null
                && postRequestDto.getImageUrl() == null
                && postRequestDto.getVideoUrl() == null) {
            return uploadOnlyTextStrategy;
        }
        else {
            return null;
        }
    }
}
