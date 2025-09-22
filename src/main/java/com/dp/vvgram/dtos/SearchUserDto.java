package com.dp.vvgram.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SearchUserDto extends BasePagingDto {
    private String username;
    private List<String> sortBy;

    public SearchUserDto() {
        super.setPageno(0);
        super.setPagesize(3);
        sortBy = List.of("username,asc");
    }
}
