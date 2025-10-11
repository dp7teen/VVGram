package com.dp.vvgram.repositories;

import com.dp.vvgram.models.BotChat;
import com.dp.vvgram.models.BotConversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BotConversationRepository extends JpaRepository<BotConversation, Long> {
}
