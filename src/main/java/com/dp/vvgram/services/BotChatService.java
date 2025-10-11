package com.dp.vvgram.services;

import org.springframework.stereotype.Service;

@Service
public interface BotChatService {
    String response(String query);
}
