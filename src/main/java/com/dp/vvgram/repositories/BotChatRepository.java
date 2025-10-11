package com.dp.vvgram.repositories;

import com.dp.vvgram.models.BotChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BotChatRepository extends JpaRepository<BotChat, Long> {

}
