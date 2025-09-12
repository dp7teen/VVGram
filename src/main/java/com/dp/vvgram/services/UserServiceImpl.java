package com.dp.vvgram.services;

import com.dp.vvgram.dtos.UpdateProfileDto;
import com.dp.vvgram.exceptions.*;
import com.dp.vvgram.models.Follow;
import com.dp.vvgram.models.User;
import com.dp.vvgram.profileUpdater.*;
import com.dp.vvgram.repositories.FollowRepository;
import com.dp.vvgram.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private List<UpdateProfile> fields;
    private final FollowRepository followRepository;

    public UserServiceImpl(UserRepository userRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder,
                           FollowRepository followRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.followRepository = followRepository;
    }

    @Override
    public User signUp(String username, String password, String email) throws UserAlreadyExistsException {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            throw new UserAlreadyExistsException("Username already exists");
        }
        Optional<User> emailUser = userRepository.findByEmail(email);
        if (emailUser.isPresent()) {
            throw new UserAlreadyExistsException("Email already exists");
        }
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setBio("");
        user.setPosts(new ArrayList<>());
        user.setFollowing(new ArrayList<>());
        user.setFollowers((new ArrayList<>()));
        return userRepository.save(user);
    }

    @Override
    public User login(String username, String password) throws UserNotFoundException, InvalidPasswordException {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("User not found with username: " + username);
        }
        User user = optionalUser.get();
        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            throw new InvalidPasswordException("Invalid password");
        }
        return user;
    }

    @Override
    public User getUserProfile(String username) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("User not found with username: " + username);
        }
        return optionalUser.get();
    }

    @Override
    public void logout(String token) {
        //todo: to be implemented
    }

    @Override
    public User updateProfile(String user,
                              UpdateProfileDto profileDto) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findByUsername(user);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("User not found with username: " + user);
        }
        User existingUser = optionalUser.get();
        initiateUpdater();
        for (UpdateProfile updateProfile : fields) {
            updateProfile.update(profileDto, existingUser);
        }
        return userRepository.save(existingUser);
    }

    private void initiateUpdater() {
        fields = List.of(
                new UserNameUpdater(), new EmailUpdater(),
                new PasswordUpdater(), new ProfilePictureUpdater(),
                new BioUpdater()
        );
    }

    @Override
    public List<User> getUsers(String username) {
        return userRepository.findAllByUsernameContainingIgnoreCase(username);
    }

    @Override
    @Transactional
    public String follow(String userOne, String userTwo) throws UserNotFoundException, UserAlreadyFollowingUserException, UserCannotFollowUserException {
        User one = getUserProfile(userOne);
        User two = getUserProfile(userTwo);

        if(!canTheyFollow(one.getId(), two.getId())) {
            throw new UserAlreadyFollowingUserException(userOne + " is already following " + userTwo);
        }

        Follow follow = new Follow();
        follow.setFollower(one);
        follow.setFollowing(two);
        followRepository.save(follow);

        one.getFollowing().add(follow);
        two.getFollowers().add(follow);

        return userOne + " is following " + userTwo + " now!";
    }

    @Override
    @Transactional
    public String unFollow(String userOne, String userTwo) throws UserNotFoundException, UserIsNotFollowingUserException, UserCannotUnfollowUserException {
        User one = getUserProfile(userOne);
        User two = getUserProfile(userTwo);

        if (!canTheyUnFollow(one.getId(), two.getId())) {
            throw new UserIsNotFollowingUserException(userOne + " is not following " + userTwo);
        }

        followRepository.deleteByFollower_IdAndFollowing_Id(one.getId(), two.getId());

        return userOne + " is unfollowing " + userTwo + " now!";
    }

    private boolean canTheyUnFollow(long userOne, long userTwo) throws UserCannotUnfollowUserException {
        if (userOne == userTwo) {
            throw new UserCannotUnfollowUserException(userOne + " cannot unfollow " + userTwo);
        }
        List<Follow> follows = followRepository.findAllByFollowerId(userOne);
        for (Follow follow : follows) {
            if (follow.getFollowing().getId() == userTwo) {
                return true;
            }
        }
        return false;
    }

    private boolean canTheyFollow(long userOne, long userTwo) throws UserCannotFollowUserException {
        if (userOne == userTwo) {
            throw new UserCannotFollowUserException(userOne + " cannot follow " + userTwo);
        }
        List<Follow> follows = followRepository.findAllByFollowerId(userOne);
        for (Follow follow : follows) {
            if (follow.getFollowing().getId() == userTwo) {
                return false;
            }
        }
        return true;
    }

    @Override
    public User getFollowers(String username) throws UserNotFoundException {
        return getUserProfile(username);
    }

    @Override
    public User getFollowing(String username) throws UserNotFoundException {
        return getUserProfile(username);
    }
}
