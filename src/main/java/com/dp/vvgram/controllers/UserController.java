package com.dp.vvgram.controllers;

import com.dp.vvgram.dtos.*;
import com.dp.vvgram.exceptions.*;
import com.dp.vvgram.models.User;
import com.dp.vvgram.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "User can signup here!  No restrictions implemented.")
    @PostMapping("/signup")
    public UserDto signUp(@RequestBody SignupRequestDto requestDto) throws UserAlreadyExistsException {
        User user = userService.signUp(
                requestDto.getUsername(),
                requestDto.getPassword(),
                requestDto.getEmail()
        );
        return UserDto.from(user);
    }

    @Operation(summary = "User can login here!  Use the correct email and password while you signed up.")
    @PostMapping("/login")
    public TokenResponseDto login(@RequestBody LoginRequestDto requestDto) throws UserNotFoundException,
            InvalidPasswordException {
        String token = userService.login(
                requestDto.getUsername(),
                requestDto.getPassword()
        );
        return TokenResponseDto.from(token);
    }

    @Operation(summary = "User can view any profile data using the corresponding username")
    @GetMapping("/user/{username}")
    public UserDto getUserProfile(@PathVariable String username) throws UserNotFoundException {
        User user = userService.getUserProfile(username);
        return UserDto.from(user);
    }

    @Operation(summary = "User can search for Users with some username!")
    @PostMapping
    public ResponseEntity<Page<UserDto>> getUserProfiles(@RequestBody SearchUserDto dto) {
        Page<UserDto> users = userService.getUsers(dto.getUsername(), dto.getPageno(),
                dto.getPagesize(),dto.getSortBy());
        return new ResponseEntity<>(
                users, HttpStatus.OK
        );
    }

    @PostMapping("/logout/{token}")
    public void logout(@PathVariable String token) {
        // Implementation for user logout
    }

    @Operation(summary = "User can update their profile.  Enter only required fields remove the unnecessary fields before executing!")
    @PatchMapping("/update/{username}")
    public UserDto updateProfile(@PathVariable String username,
                                 @RequestBody UpdateProfileDto updateProfileDto)
    throws UserNotFoundException{
        User user = userService.updateProfile(username, updateProfileDto);
        return UserDto.from(user);
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
