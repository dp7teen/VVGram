package com.dp.vvgram.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ShowCommentsDto extends BasePagingDto {
    private Long postid;
    private List<String> sortBy;

    public ShowCommentsDto() {
        super.setPageno(0);
        super.setPagesize(3);
        sortBy = List.of("createdAt,desc");
    }
}
