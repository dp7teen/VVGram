package com.dp.vvgram.services;

import com.dp.vvgram.exceptions.UserNotFoundException;
import com.dp.vvgram.models.Post;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FeedService {
    List<Post> getPostsByUser(String username) throws UserNotFoundException;
}
