package com.dp.vvgram.dtos;

import com.dp.vvgram.models.Follow;
import com.dp.vvgram.models.User;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class FollowDto {
    private List<UserInFollowList> users;

    public static FollowDto from (User user) {
        FollowDto dto = new FollowDto();
        dto.setUsers(new ArrayList<>());

        for (Follow follow : user.getFollowers()) {
            UserInFollowList userInFollowList = new UserInFollowList();
            userInFollowList.setUsername(follow.getFollowing().getUsername());
            userInFollowList.setProfilePic(follow.getFollowing().getProfilePictureUrl());
            userInFollowList.setFollowersCount(follow.getFollowing().getFollowers().size());
            dto.getUsers().add(userInFollowList);
        }
        return dto;
    }
}
