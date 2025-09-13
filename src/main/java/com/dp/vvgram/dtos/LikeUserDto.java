package com.dp.vvgram.dtos;

import com.dp.vvgram.models.Like;
import com.dp.vvgram.models.User;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class LikeUserDto {
    private long id;
    private String username;

    public static List<LikeUserDto> from(List<Like> likes){
        List<LikeUserDto> dtos = new ArrayList<>();
        for (Like like : likes) {
            LikeUserDto dto = new LikeUserDto();
            dto.setId(like.getUser().getId());
            dto.setUsername(like.getUser().getUsername());
            dtos.add(dto);
        }
        return dtos;
    }
}
