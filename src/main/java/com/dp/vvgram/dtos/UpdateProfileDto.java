package com.dp.vvgram.dtos;

import com.dp.vvgram.models.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProfileDto {
    private String username;
    private String password;
    private String email;
    private String profilePictureUrl;
    private String bio;

}
