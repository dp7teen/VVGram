package com.dp.vvgram.repositories;

import com.dp.vvgram.models.Post;
import com.dp.vvgram.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findByIdAndAuthor_Username(Long id, String authorUsername);

    List<Post> findByAuthor_Username(String username);
}
