package com.dp.vvgram.services;

import com.dp.vvgram.exceptions.UserNotFoundException;
import com.dp.vvgram.models.Post;
import com.dp.vvgram.models.User;
import com.dp.vvgram.repositories.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedServiceImpl implements FeedService {
    private final PostRepository postRepository;
    private final UserService userService;

    public FeedServiceImpl(PostRepository postRepository,
                           UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    @Override
    public List<Post> getPostsByUser(String username) throws UserNotFoundException {
        User user = userService.getUserProfile(username);

        return postRepository.findPostsByCurrentUser(user.getId());
    }
}
