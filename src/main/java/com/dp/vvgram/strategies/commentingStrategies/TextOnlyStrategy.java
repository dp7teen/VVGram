package com.dp.vvgram.strategies.commentingStrategies;

import com.dp.vvgram.dtos.CommentRequestDto;
import com.dp.vvgram.exceptions.PostNotFoundException;
import com.dp.vvgram.exceptions.UserNotFoundException;
import com.dp.vvgram.models.Comment;
import com.dp.vvgram.models.Post;
import com.dp.vvgram.models.User;
import com.dp.vvgram.repositories.CommentRepository;
import com.dp.vvgram.repositories.PostRepository;
import com.dp.vvgram.services.CommentTypeService;
import com.dp.vvgram.services.PostService;
import com.dp.vvgram.services.UserService;
import com.dp.vvgram.services.UserServiceImpl;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TextOnlyStrategy implements CommentTypeService {
    private UserService userService;
    private PostRepository postRepository;
    private final CommentRepository commentRepository;

    public TextOnlyStrategy(UserService userService,
                            PostRepository postRepository,
                            CommentRepository commentRepository) {
        this.userService = userService;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }
    @Override
    public String comment(long postId, String username, CommentRequestDto commentRequestDto) throws UserNotFoundException, PostNotFoundException {
        User user = userService.getUserProfile(username);
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isEmpty()) {
            throw new PostNotFoundException("Post with id " + postId + " is not found!");
        }

        Post post = optionalPost.get();

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setText(commentRequestDto.getText());
        comment.setImageUrl("");
        comment.setGifUrl("");
        comment.setUser(user);

        post.getComments().add(comment);

        commentRepository.save(comment);
        return "You've just commented to this post " + postId + "!";
    }
}
