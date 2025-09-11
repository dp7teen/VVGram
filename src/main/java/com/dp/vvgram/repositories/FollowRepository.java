package com.dp.vvgram.repositories;

import com.dp.vvgram.models.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    List<Follow> findAllByFollowerId(Long userOne);

    void deleteByFollower_IdAndFollowing_Id(Long followerId, Long followingId);
}
