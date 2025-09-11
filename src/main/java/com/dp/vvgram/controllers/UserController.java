package com.dp.vvgram.controllers;

import com.dp.vvgram.dtos.*;
import com.dp.vvgram.exceptions.*;
import com.dp.vvgram.models.User;
import com.dp.vvgram.services.UserService;
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

    @PostMapping("/signup")
    public UserDto signUp(@RequestBody SignupRequestDto requestDto) throws UserAlreadyExistsException {
        User user = userService.signUp(
                requestDto.getUsername(),
                requestDto.getPassword(),
                requestDto.getEmail()
        );
        return UserDto.from(user);
    }

    @PostMapping("/login")
    public UserDto login(@RequestBody LoginRequestDto requestDto) throws UserNotFoundException, InvalidPasswordException {
        User user = userService.login(
                requestDto.getUsername(),
                requestDto.getPassword()
        );
        return UserDto.from(user);
    }

    @GetMapping("/user/{username}")
    public UserDto getUserProfile(@PathVariable String username) throws UserNotFoundException {
        User user = userService.getUserProfile(username);
        return UserDto.from(user);
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<UserDto>> getUserProfiles(@PathVariable String username) {
        List<User> users = userService.getUsers(username);
        List<UserDto> userDtos = new ArrayList<>();
        if (!users.isEmpty()) {
            for (User user : users) {
                userDtos.add(UserDto.from(user));
            }
        }
        return new ResponseEntity<>(
                userDtos, HttpStatus.OK
        );
    }

    @PostMapping("/logout/{token}")
    public void logout(@PathVariable String token) {
        // Implementation for user logout
    }

    @PatchMapping("/update/{username}")
    public UserDto updateProfile(@PathVariable String username,
                                 @RequestBody UpdateProfileDto updateProfileDto)
    throws UserNotFoundException{
        User user = userService.updateProfile(username, updateProfileDto);
        return UserDto.from(user);
    }

    @PostMapping("/follow")
    public ResponseEntity<String> follow(@RequestParam("user") String userOne,
                                         @RequestParam("follow") String userTwo) throws UserNotFoundException, UserAlreadyFollowingUserException, UserCannotFollowUserException {
        String message = userService.follow(userOne, userTwo);
        return new ResponseEntity<>(
                message ,
                HttpStatus.OK
        );
    }

    @PostMapping("/unfollow")
    public ResponseEntity<String> unFollow(@RequestParam("user") String userOne,
                                           @RequestParam("unfollow") String userTwo) throws UserNotFoundException, UserCannotUnfollowUserException, UserIsNotFollowingUserException {
        return new ResponseEntity<>(
                userService.unFollow(userOne, userTwo),
                HttpStatus.OK
        );
    }

    @GetMapping("/followers/{username}")
    public FollowDto getFollowers(@PathVariable String username) throws UserNotFoundException {
        return null;
        //todo
    }

    @GetMapping("/following/{username}")
    public FollowDto getFollowing(@PathVariable String username) throws UserNotFoundException {
        return null;
        //todo
    }
}
