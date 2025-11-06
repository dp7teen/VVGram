package com.dp.vvgram.controllers;

import com.dp.vvgram.dtos.FeedDto;
import com.dp.vvgram.exceptions.UserNotFoundException;
import com.dp.vvgram.services.FeedService;
import com.dp.vvgram.utilities.PrincipalHelper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/feed")
public class FeedController {
    private final FeedService feedService;

    public FeedController(FeedService feedService) {
        this.feedService = feedService;
    }

    @GetMapping("/home")
    public List<FeedDto> getPostsOfUser() throws UserNotFoundException {
        UserDetails details = PrincipalHelper.getPrincipal();
        return FeedDto.from(feedService.getPostsByUser(details.getUsername()));
    }
}
