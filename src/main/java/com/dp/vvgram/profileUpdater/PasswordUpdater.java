package com.dp.vvgram.profileUpdater;

import com.dp.vvgram.dtos.UpdateProfileDto;
import com.dp.vvgram.models.User;
import org.springframework.stereotype.Component;

@Component
public class PasswordUpdater implements UpdateProfile {
    @Override
    public void update(UpdateProfileDto profileDto, User user) {
        if (profileDto.getPassword() != null) {
            user.setPassword(profileDto.getPassword());
        }
    }
}
