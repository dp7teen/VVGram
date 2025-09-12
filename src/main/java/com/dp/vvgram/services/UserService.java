package com.dp.vvgram.services;

import com.dp.vvgram.dtos.UpdateProfileDto;
import com.dp.vvgram.exceptions.*;
import com.dp.vvgram.models.User;
import com.dp.vvgram.profileUpdater.UpdateProfile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    User signUp(String username, String password, String email) throws UserAlreadyExistsException;

    User login(String username, String password) throws UserNotFoundException, InvalidPasswordException;

    User getUserProfile(String username) throws UserNotFoundException;

    void logout(String token);

    User updateProfile(String user, UpdateProfileDto profile) throws UserNotFoundException;

    List<User> getUsers(String username);

    String follow(String userOne, String userTwo) throws UserNotFoundException, UserAlreadyFollowingUserException, UserCannotFollowUserException;

    String unFollow(String userOne, String userTwo) throws UserNotFoundException, UserIsNotFollowingUserException,
            UserCannotUnfollowUserException;

    User getFollowers(String username)throws UserNotFoundException;

    User getFollowing(String username)throws UserNotFoundException;
}
