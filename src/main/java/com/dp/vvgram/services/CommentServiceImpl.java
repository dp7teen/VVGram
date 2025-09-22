package com.dp.vvgram.services;

import com.dp.vvgram.dtos.CommentDto;
import com.dp.vvgram.dtos.CommentRequestDto;
import com.dp.vvgram.exceptions.CommentNotFoundException;
import com.dp.vvgram.exceptions.PostNotFoundException;
import com.dp.vvgram.exceptions.UserNotFoundException;
import com.dp.vvgram.factory.CommentStrategyFactory;
import com.dp.vvgram.helpers.OrderByHelper;
import com.dp.vvgram.models.Comment;
import com.dp.vvgram.models.Post;
import com.dp.vvgram.repositories.CommentRepository;
import com.dp.vvgram.repositories.PostRepository;
import com.dp.vvgram.services.commentEditor.CommentEditor;
import com.dp.vvgram.services.commentEditor.GifEditor;
import com.dp.vvgram.services.commentEditor.ImageEditor;
import com.dp.vvgram.services.commentEditor.TextEditor;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService{
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final PostRepository postRepository;
    private List<CommentEditor> commentEditors;

    public CommentServiceImpl(CommentRepository commentRepository,
                              UserService userService,
                              PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.postRepository = postRepository;
    }

    public Comment getComment(long postId, String username) throws CommentNotFoundException, UserNotFoundException {
        userService.getUserProfile(username);
        Optional<Comment> optionalComment = commentRepository.findByPost_IdAndUser_Username(postId, username);
        if (optionalComment.isEmpty()) {
            throw new CommentNotFoundException("Comment is not found!");
        }
        return optionalComment.get();
    }

    public Comment getCommentById(long commentId) throws CommentNotFoundException {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        if (optionalComment.isEmpty()) {
            throw new CommentNotFoundException("Comment is not found!");
        }
        return optionalComment.get();
    }

    @Override
    @Transactional
    public String uncomment(long postId, long commentId) throws CommentNotFoundException, UserNotFoundException, PostNotFoundException {
        Comment comment = getCommentById(commentId);

        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isEmpty()) {
            throw new PostNotFoundException("Post with " + postId + " is not found");
        }

        Post post = optionalPost.get();
        post.getComments().remove(comment);

        commentRepository.delete(comment);
        return "The comment is successfully deleted!";
    }

    @Override
    public String editComment(long commentId, CommentRequestDto dto) throws UserNotFoundException, CommentNotFoundException {
        Comment comment = getCommentById(commentId);

        initiateCommentEditors();
        for (CommentEditor commentEditor : commentEditors) {
            commentEditor.editComment(dto, comment);
        }
        commentRepository.save(comment);
        return "The Comment edited successfully";
    }

    @Override
    public String comment(long postId, String username, CommentRequestDto commentRequestDto) throws UserNotFoundException, PostNotFoundException {
        CommentTypeService commentService = CommentStrategyFactory.getCommentTypeService(commentRequestDto);
        if (commentService == null) {
            return "The Expected service is not available!";
        }

        return commentService.comment(postId, username, commentRequestDto);
    }

    @Override
    public Page<CommentDto> getComments(long postId, int pageNo,
                                        int pageSize, List<String> sortBy) throws PostNotFoundException {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isEmpty()) {
            throw new PostNotFoundException("Post with " + postId + " is not found");
        }
        List<Sort.Order> orders = OrderByHelper.orderBy(sortBy);

        Page<Comment> commentsPaged = commentRepository.findByPost_Id(postId,
                PageRequest.of(pageNo, pageSize).withSort(Sort.by(orders)));
        List<CommentDto> comments = CommentDto.from(commentsPaged.getContent());
        return new PageImpl<>(comments, commentsPaged.getPageable(), commentsPaged.getTotalElements());
    }

    private void initiateCommentEditors() {
        commentEditors = List.of(
                new TextEditor()
        );
    }
}
