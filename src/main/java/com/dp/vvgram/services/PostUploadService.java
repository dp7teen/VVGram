package com.dp.vvgram.services;

import com.dp.vvgram.dtos.PostRequestDto;
import com.dp.vvgram.exceptions.PostingServiceNotAvailableException;
import com.dp.vvgram.exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;

@Service
public interface PostUploadService {
    String upload(String username, PostRequestDto postRequestDto) throws UserNotFoundException, PostingServiceNotAvailableException;
}
