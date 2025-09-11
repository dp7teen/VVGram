package com.dp.vvgram.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "comments")
public class Comment extends BaseModel {
    private String text;
    @ManyToOne
    private Post post;
    @ManyToOne
    private User user;
}
