package com.dp.vvgram.services;

import com.dp.vvgram.dtos.PostDto;
import com.dp.vvgram.dtos.PostRequestDto;
import com.dp.vvgram.exceptions.PostNotFoundException;
import com.dp.vvgram.exceptions.PostingServiceNotAvailableException;
import com.dp.vvgram.exceptions.UserNotFoundException;
import com.dp.vvgram.factory.PostUploadFactory;
import com.dp.vvgram.models.Post;
import com.dp.vvgram.models.User;
import com.dp.vvgram.repositories.PostRepository;
import com.dp.vvgram.repositories.UserRepository;
import com.dp.vvgram.services.postEditor.ContentEditor;
import com.dp.vvgram.services.postEditor.PostEditor;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService{
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private List<PostEditor> postEditors;

    public PostServiceImpl(UserRepository userRepository,
                           PostRepository postRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    private User getUser(String username) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException(username + " is not found!");
        }
        return optionalUser.get();
    }

    private Post getPost(String username, long postId) throws PostNotFoundException {
        Optional<Post> optionalPost = postRepository.findByIdAndAuthor_Username(postId, username);
        if (optionalPost.isEmpty()) {
            throw new PostNotFoundException(postId + " is not found!");
        }
        return optionalPost.get();
    }

    @Override
    public String upload(String username, PostRequestDto postRequestDto) throws UserNotFoundException, PostingServiceNotAvailableException {
        PostUploadService uploadService = PostUploadFactory.getPostUploadingService(postRequestDto);
        if (uploadService == null) {
            throw new PostingServiceNotAvailableException("The Expected service is not available!");
        }
        return uploadService.upload(username, postRequestDto);
    }

    @Override
    @Transactional
    public String editPost(String username,
                           long postId,
                           PostRequestDto postRequestDto) throws UserNotFoundException, PostNotFoundException {

        User user = getUser(username);
        Post post = getPost(username, postId);

        initiatePostEditors();

        for (PostEditor postEditor : postEditors) {
            postEditor.edit(post, postRequestDto);
        }
        post.setLastModifiedAt(new Date());
        postRepository.save(post);
        user.getPosts().add(post);

        return "Post edited successfully!";
    }

    @Override
    @Transactional
    public String delete(String username, long postId) throws UserNotFoundException, PostNotFoundException {

        Post post = getPost(username, postId);
        postRepository.delete(post);

        User user = getUser(username);
        user.getPosts().remove(post);

        return "Post deleted successfully!";
    }

    private void initiatePostEditors() {
        postEditors = List.of(
                new ContentEditor()
        );
    }

    @Override
    public Page<PostDto> getPostsByUser(String username,
                                        int pageNo,
                                        int pageSize,
                                        List<String> sortBy) throws UserNotFoundException {
        getUser(username);
        List<Sort.Order> orderBy = sortBy.stream()
                .map(s -> {
                    String[] parts = s.split(",");
                    String field = parts[0];
                    Sort.Direction direction = parts.length > 1 && parts[1].equals("asc")
                            ? Sort.Direction.ASC : Sort.Direction.DESC;
                    return new Sort.Order(direction, field);
                })
                .toList();

        Page<Post> postPages = postRepository.findByAuthor_UsernameContaining(username,
                PageRequest.of(pageNo, pageSize).withSort(Sort.by(orderBy)));
        List<PostDto> posts = PostDto.from(postPages.getContent());

        return new PageImpl<>(posts, postPages.getPageable(), postPages.getTotalElements());
    }

    @Override
    public Post getPostById(String username, long id) throws UserNotFoundException, PostNotFoundException {
        getUser(username);
        return getPost(username, id);
    }
}
