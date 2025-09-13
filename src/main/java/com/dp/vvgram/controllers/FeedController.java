package com.dp.vvgram.controllers;

import com.dp.vvgram.dtos.FeedDto;
import com.dp.vvgram.exceptions.UserNotFoundException;
import com.dp.vvgram.services.FeedService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/feed/home")
public class FeedController {
    private final FeedService feedService;

    public FeedController(FeedService feedService) {
        this.feedService = feedService;
    }

    @GetMapping("/{username}")
    public List<FeedDto> getPostsOfUser(@PathVariable String username) throws UserNotFoundException {
        return FeedDto.from(feedService.getPostsByUser(username));
    }
}
