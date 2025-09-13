package com.dp.vvgram.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity(name = "users")
public class User extends BaseModel {
    @Column(unique = true)
    private String username;
    private String email;
    private String password;
    private String bio;
    private String profilePictureUrl;
    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private List<Post> posts;
    @OneToMany(mappedBy = "following", fetch = FetchType.LAZY)
    private List<Follow> followers;
    @OneToMany(mappedBy = "follower", fetch = FetchType.LAZY)
    private List<Follow> following;
}
