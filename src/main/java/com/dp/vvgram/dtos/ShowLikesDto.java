package com.dp.vvgram.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ShowLikesDto extends BasePagingDto {
    private Long postid;
    private List<String> sortBy;

    public ShowLikesDto() {
        super.setPageno(0);
        super.setPagesize(3);
        sortBy = List.of("createdAt,desc");
    }
}
