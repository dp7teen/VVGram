package com.dp.vvgram.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity(name = "posts")
public class Post extends BaseModel {
    @Lob
    private String content;
    @ManyToOne
    private User author;
    @OneToMany(mappedBy = "post")
    private List<Comment> comments;
    @OneToMany(mappedBy = "post")
    private List<Like> likes;
    private Date timestamp;
}
