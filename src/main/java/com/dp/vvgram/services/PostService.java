package com.dp.vvgram.services;

import com.dp.vvgram.dtos.PostDto;
import com.dp.vvgram.dtos.PostRequestDto;
import com.dp.vvgram.exceptions.PostNotFoundException;
import com.dp.vvgram.exceptions.UserNotFoundException;
import com.dp.vvgram.models.Post;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostService extends PostUploadService{
    String editPost(String username, long postId, PostRequestDto postRequestDto) throws UserNotFoundException, PostNotFoundException;

    String delete(String username, long postId) throws UserNotFoundException, PostNotFoundException;

    Page<PostDto> getPostsByUser(String username, int pageNo, int pageSize, List<String> sortBy) throws UserNotFoundException;

    Post getPostById(String username, long id) throws UserNotFoundException, PostNotFoundException;
}
