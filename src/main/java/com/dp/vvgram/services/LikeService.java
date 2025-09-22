package com.dp.vvgram.services;

import com.dp.vvgram.dtos.LikeUserDto;
import com.dp.vvgram.exceptions.LikeNotFoundException;
import com.dp.vvgram.exceptions.PostNotFoundException;
import com.dp.vvgram.exceptions.UserNotFoundException;
import com.dp.vvgram.models.Like;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LikeService {
    String like(long postId, String username) throws UserNotFoundException, PostNotFoundException;

    String unLike(long postId, String username) throws UserNotFoundException, PostNotFoundException, LikeNotFoundException;

    Page<LikeUserDto> getLikes(long postId, int pageno, int pagesize, List<String> sortBy) throws PostNotFoundException;
}
