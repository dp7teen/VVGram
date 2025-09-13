package com.dp.vvgram.dtos;

import com.dp.vvgram.models.Post;
import com.dp.vvgram.models.User;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class FeedDto {
    private long postId;
    private String text;
    private UserDto author;
    private int likesCount;
    private int commentsCount;
    private Date time;

    public static List<FeedDto> from (List<Post> posts) {
        List<FeedDto> feedDtos = new ArrayList<>();
        for (Post post : posts) {
            FeedDto feedDto = new FeedDto();
            feedDto.setPostId(post.getId());
            feedDto.setText(post.getContent());
            UserDto userDto = UserDto.from(post.getAuthor());
            feedDto.setAuthor(userDto);
            feedDto.setLikesCount(post.getLikes().size());
            feedDto.setCommentsCount(post.getComments().size());
            feedDto.setTime(post.getTimestamp());
            feedDtos.add(feedDto);
        }
        return feedDtos;
    }
}
