package com.dp.vvgram.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "follows")
public class Follow extends BaseModel {
    @ManyToOne
    private User follower;
    @ManyToOne
    private User following;
}
