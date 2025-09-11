package com.dp.vvgram.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInFollowList {
    private String username;
    private long followersCount;
    private String profilePic;
}
