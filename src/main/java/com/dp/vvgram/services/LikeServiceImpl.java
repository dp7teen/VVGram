package com.dp.vvgram.services;

import com.dp.vvgram.dtos.LikeUserDto;
import com.dp.vvgram.exceptions.LikeNotFoundException;
import com.dp.vvgram.exceptions.PostNotFoundException;
import com.dp.vvgram.exceptions.UserNotFoundException;
import com.dp.vvgram.helpers.OrderByHelper;
import com.dp.vvgram.models.Like;
import com.dp.vvgram.models.Post;
import com.dp.vvgram.models.User;
import com.dp.vvgram.repositories.LikeRepository;
import com.dp.vvgram.repositories.PostRepository;
import com.dp.vvgram.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LikeServiceImpl implements LikeService{
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public LikeServiceImpl(LikeRepository likeRepository,
                           UserRepository userRepository,
                           PostRepository postRepository) {
        this.likeRepository = likeRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    private User getUser(String username) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("User '" + username + "' is not found!");
        }
        return optionalUser.get();
    }

    private Post getPost(long id, String username) throws PostNotFoundException {
        Optional<Post> optionalPost = postRepository.findByIdAndAuthor_Username(id, username);
        if (optionalPost.isEmpty()) {
            throw new PostNotFoundException("Post '" + id + "' is not found!");
        }
        return optionalPost.get();
    }

    private Post getPost(long id) throws PostNotFoundException {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isEmpty()) {
            throw new PostNotFoundException("Post '" + id + "' is not found!");
        }
        return optionalPost.get();
    }


    @Override
    public String like(long postId, String username) throws UserNotFoundException, PostNotFoundException {
        User user = getUser(username);
        Post post = getPost(postId);

        Optional<Like> optionalLike = likeRepository.findByUser_UsernameAndPost_Id(username, postId);
        if (optionalLike.isPresent()) {
            return "Liked!";
        }

        Like like = new Like();
        like.setPost(post);
        like.setUser(user);

        post.getLikes().add(like);
        post.setLastModifiedAt(new Date());
        likeRepository.save(like);
        return "Liked!";
    }

    @Override
    @Transactional
    public String unLike(long postId, String username) throws UserNotFoundException, PostNotFoundException, LikeNotFoundException {
        getUser(username);
        Post post = getPost(postId);

        Optional<Like> optionalLike = likeRepository.findByUser_UsernameAndPost_Id(username, postId);
        if (optionalLike.isEmpty()) {
            throw new LikeNotFoundException("Cannot un-like the post");
        }
        Like like = optionalLike.get();
        likeRepository.delete(like);
        post.getLikes().remove(like);
        post.setLastModifiedAt(new Date());
        return "UnLiked!";
    }

    @Override
    public Page<LikeUserDto> getLikes(long postId, int pageno, int pagesize,
                                      List<String> sortBy) throws PostNotFoundException {
        getPost(postId);
        List<Sort.Order> orders = OrderByHelper.orderBy(sortBy);

        Page<Like> likePaged = likeRepository.findAllByPost_Id(postId,
                PageRequest.of(pageno, pagesize).withSort(Sort.by(orders)));
        List<LikeUserDto> likeUserDtos = LikeUserDto.from(likePaged.getContent());
        return new PageImpl<>(likeUserDtos, likePaged.getPageable(), likePaged.getTotalElements());
    }
}
