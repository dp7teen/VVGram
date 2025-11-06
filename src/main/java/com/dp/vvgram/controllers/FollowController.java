package com.dp.vvgram.controllers;

import com.dp.vvgram.dtos.FollowDto;
import com.dp.vvgram.exceptions.*;
import com.dp.vvgram.services.UserService;
import com.dp.vvgram.utilities.PrincipalHelper;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/follows")
public class FollowController {
    private UserService userService;

    public FollowController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "User can follow another user.  Enter user to be followed.")
    @PostMapping("/follow")
    public ResponseEntity<String> follow(@RequestParam("follow") String user) throws UserNotFoundException,
            UserAlreadyFollowingUserException, UserCannotFollowUserException {
        UserDetails userDetails = PrincipalHelper.getPrincipal();
        String message = userService.follow(userDetails.getUsername(), user);
        return new ResponseEntity<>(
                message ,
                HttpStatus.OK
        );
    }

    @Operation(summary = "User can unfollow another user.  Enter user to be unfollowed.")
    @PostMapping("/unfollow")
    public ResponseEntity<String> unFollow(@RequestParam("unfollow") String user) throws UserNotFoundException,
            UserCannotUnfollowUserException, UserIsNotFollowingUserException {
        UserDetails userDetails = PrincipalHelper.getPrincipal();
        return new ResponseEntity<>(
                userService.unFollow(userDetails.getUsername(), user),
                HttpStatus.OK
        );
    }

    @Operation(summary = "User can check their followers.")
    @GetMapping("/followers")
    public FollowDto getFollowers() throws UserNotFoundException {
        UserDetails userDetails = PrincipalHelper.getPrincipal();
        return FollowDto.fromFollower(userService.getFollowers(userDetails.getUsername()));
    }

    @Operation(summary = "User can check who they're following.")
    @GetMapping("/following")
    public FollowDto getFollowing() throws UserNotFoundException {
        UserDetails userDetails = PrincipalHelper.getPrincipal();
        return FollowDto.fromFollowing(userService.getFollowing(userDetails.getUsername()));
    }
}
