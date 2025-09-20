package com.dp.vvgram.services;

import com.dp.vvgram.exceptions.UserNotFoundException;
import com.dp.vvgram.models.Post;
import com.dp.vvgram.models.User;
import com.dp.vvgram.repositories.PostRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

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

        List<Post> posts = postRepository.findPostsByCurrentUser(user.getId());
//        PriorityQueue<Post> mostRecentOrder = new PriorityQueue<>(
//                (a,b) -> b.getCreatedAt().compareTo(a.getCreatedAt())
//        );
//
//        mostRecentOrder.addAll(posts);
//        posts.clear();
//        while(!mostRecentOrder.isEmpty()) {
//            posts.add(mostRecentOrder.remove());
//        }
        Collections.sort(posts, (a,b) -> b.getCreatedAt().compareTo(a.getCreatedAt()));
        return posts;
    }
}
