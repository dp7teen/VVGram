package com.dp.vvgram.dtos;

import com.dp.vvgram.models.Post;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PostDto {
    private long id;
    private String content = "";
    private String imageUrl = "";
    private String videoUrl = "";
    private String authorName = "";
    private Long likeCount;
    private Long commentCount;

    public static List<PostDto> from(List<Post> posts) {
        List<PostDto> postDtos = new ArrayList<>();
        for (Post post : posts) {
            PostDto dto = new PostDto();
            dto.setId(post.getId());
            dto.setContent(post.getContent());
            dto.setAuthorName(post.getAuthor().getUsername());
            dto.setLikeCount(post.getLikes().stream().count());
            dto.setCommentCount(post.getComments().stream().count());
            postDtos.add(dto);
        }
        return postDtos;
    }
}
