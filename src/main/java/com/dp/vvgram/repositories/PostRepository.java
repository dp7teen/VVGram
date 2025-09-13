package com.dp.vvgram.repositories;

import com.dp.vvgram.models.Post;
import com.dp.vvgram.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findByIdAndAuthor_Username(Long id, String authorUsername);

    List<Post> findByAuthor_Username(String username);

    @Query("""
            select p from posts p
                        where p.author.id = :userId
                                    or p.author.id in (
                                                    select f.following.id
                                                                from follows f
                                                                            where f.follower.id = :userId
                                                )
            """
    )
    List<Post> findPostsByCurrentUser(@Param("userId") Long userId);
}
