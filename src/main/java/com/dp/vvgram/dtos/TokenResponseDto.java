package com.dp.vvgram.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenResponseDto {
    private String token;

    public static TokenResponseDto from(String token) {
        TokenResponseDto dto = new TokenResponseDto();
        dto.token = token;
        return dto;
    }
}
