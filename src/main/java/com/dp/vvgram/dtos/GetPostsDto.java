package com.dp.vvgram.dtos;

import com.dp.vvgram.models.Post;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class GetPostsDto {
    private String username;
    private int pageNo;
    private int pageSize;
    private List<String> sortBy;

    public GetPostsDto() {
        pageNo = 0;
        pageSize = 5;
        sortBy = List.of("createdAt,asc");
    }

}
