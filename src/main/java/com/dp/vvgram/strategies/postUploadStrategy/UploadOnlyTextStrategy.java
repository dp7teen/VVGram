package com.dp.vvgram.strategies.postUploadStrategy;

import com.dp.vvgram.dtos.PostRequestDto;
import com.dp.vvgram.exceptions.UserNotFoundException;
import com.dp.vvgram.models.Post;
import com.dp.vvgram.models.User;
import com.dp.vvgram.repositories.PostRepository;
import com.dp.vvgram.repositories.UserRepository;
import com.dp.vvgram.services.PostUploadService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Component("uploadTextOnlyStrategy")
public class UploadOnlyTextStrategy implements PostUploadService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;


    public UploadOnlyTextStrategy(UserRepository userRepository,
                          PostRepository postRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public String upload(String username, PostRequestDto postRequestDto) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException(username + " is not found");
        }
        User user = optionalUser.get();
        Post post = new Post();
        post.setContent(postRequestDto.getContent());
        post.setAuthor(user);
        post.setComments(new ArrayList<>());
        post.setLikes(new ArrayList<>());
        post.setTimestamp(new Date());
        post.setLastModifiedAt(new Date());
        postRepository.save(post);
        user.getPosts().add(post);

        return "Post successfully created!";
    }
}
