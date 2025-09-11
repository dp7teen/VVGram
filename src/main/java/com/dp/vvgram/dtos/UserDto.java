package com.dp.vvgram.dtos;

import com.dp.vvgram.models.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private Long id;
    private String username;
    private String bio;
    private String profilePictureUrl;
    private int postCount;
    private int followerCount;
    private int followingCount;

    public static UserDto from(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setBio(user.getBio());
        userDto.setProfilePictureUrl(user.getProfilePictureUrl());
        userDto.setPostCount(user.getPosts() != null ? user.getPosts().size() : 0);
        userDto.setFollowerCount(user.getFollowers().size());
        userDto.setFollowingCount(user.getFollowing().size());
        return userDto;
    }

}
