package com.dp.vvgram.repositories;

import com.dp.vvgram.models.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    List<Like> findAllByPost_Id(Long post_id);

    Optional<Like> findByUser_UsernameAndPost_Id(String userUsername, Long postId);
}
