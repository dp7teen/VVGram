package com.dp.vvgram.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity(name = "posts")
public class Post extends BaseModel {
    private String content;
    @ManyToOne
    private User user;
    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER)
    private List<Comment> comments;
    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER)
    private List<Like> likes;
    private Date timestamp;
}
