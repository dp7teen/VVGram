package com.dp.vvgram.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "likes")
public class Like extends BaseModel {
    @ManyToOne
    private Post post;
    @ManyToOne
    private User user;
}
