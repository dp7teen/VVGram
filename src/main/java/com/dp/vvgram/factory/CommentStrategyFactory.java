package com.dp.vvgram.factory;

import com.dp.vvgram.dtos.CommentRequestDto;
import com.dp.vvgram.services.CommentTypeService;
import com.dp.vvgram.strategies.commentingStrategies.TextOnlyStrategy;
import org.springframework.stereotype.Component;

@Component
public class CommentStrategyFactory {
    private static TextOnlyStrategy textOnlyStrategy;

    public CommentStrategyFactory(TextOnlyStrategy textOnlyStrategy) {
        CommentStrategyFactory.textOnlyStrategy = textOnlyStrategy;
    }

    public static CommentTypeService getCommentTypeService(CommentRequestDto dto) {
        if (dto.getGifUrl()==null && dto.getImageUrl()==null) {
            return textOnlyStrategy;
        }
        else {
            return null;
        }
    }
}
