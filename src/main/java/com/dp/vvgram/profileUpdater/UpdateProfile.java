package com.dp.vvgram.profileUpdater;

import com.dp.vvgram.dtos.UpdateProfileDto;

import com.dp.vvgram.models.User;
import org.springframework.stereotype.Component;

@Component
public interface UpdateProfile {
    void update(UpdateProfileDto profileDto, User user);
}
