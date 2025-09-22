package com.dp.vvgram.repositories;

import com.dp.vvgram.models.Comment;
import com.dp.vvgram.models.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByPost_IdAndUser_Username(Long postId, String userUsername);

    Long post(Post post);

    Page<Comment> findByPost_Id(long postId, Pageable pageable);
}
