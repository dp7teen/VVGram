package com.dp.vvgram.controllers;

import com.dp.vvgram.dtos.FollowDto;
import com.dp.vvgram.exceptions.*;
import com.dp.vvgram.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/follows")
public class FollowController {
    private UserService userService;

    public FollowController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "User can follow another user.  First enter current user, next user to be followed.")
    @PostMapping("/follow")
    public ResponseEntity<String> follow(@RequestParam("user") String userOne,
                                         @RequestParam("follow") String userTwo) throws UserNotFoundException, UserAlreadyFollowingUserException, UserCannotFollowUserException {
        String message = userService.follow(userOne, userTwo);
        return new ResponseEntity<>(
                message ,
                HttpStatus.OK
        );
    }

    @Operation(summary = "User can unfollow another user.  First enter current user, next user to be unfollowed.")
    @PostMapping("/unfollow")
    public ResponseEntity<String> unFollow(@RequestParam("user") String userOne,
                                           @RequestParam("unfollow") String userTwo) throws UserNotFoundException, UserCannotUnfollowUserException, UserIsNotFollowingUserException {
        return new ResponseEntity<>(
                userService.unFollow(userOne, userTwo),
                HttpStatus.OK
        );
    }

    @Operation(summary = "User can check their followers.")
    @GetMapping("/followers/{username}")
    public FollowDto getFollowers(@PathVariable String username) throws UserNotFoundException {
        return FollowDto.fromFollower(userService.getFollowers(username));
    }

    @Operation(summary = "User can can check who they're following.")
    @GetMapping("/following/{username}")
    public FollowDto getFollowing(@PathVariable String username) throws UserNotFoundException {
        return FollowDto.fromFollowing(userService.getFollowing(username));
    }
}
