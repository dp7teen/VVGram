package com.dp.vvgram.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BasePagingDto {
    private int pageno;
    private int pagesize;
}
