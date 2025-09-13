package com.dp.vvgram.services;

import com.dp.vvgram.exceptions.LikeNotFoundException;
import com.dp.vvgram.exceptions.PostNotFoundException;
import com.dp.vvgram.exceptions.UserNotFoundException;
import com.dp.vvgram.models.Like;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LikeService {
    String like(long postId, String username) throws UserNotFoundException, PostNotFoundException;

    String unLike(long postId, String username) throws UserNotFoundException, PostNotFoundException, LikeNotFoundException;

    List<Like> getLikes(long postId) throws PostNotFoundException;
}
