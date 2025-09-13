package com.dp.vvgram.repositories;

import com.dp.vvgram.models.Comment;
import com.dp.vvgram.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByPost_IdAndUser_Username(Long postId, String userUsername);

    Long post(Post post);

    List<Comment> findByPost_Id(long postId);
}
