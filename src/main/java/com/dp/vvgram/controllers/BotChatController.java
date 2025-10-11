package com.dp.vvgram.controllers;

import com.dp.vvgram.dtos.BotQueryDto;
import com.dp.vvgram.services.BotChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/botchats")
public class BotChatController {
    private final BotChatService botChatService;

    public BotChatController(BotChatService botChatService) {
        this.botChatService = botChatService;
    }

    @GetMapping
    public ResponseEntity<Map<String, String>> getResponse(@RequestBody BotQueryDto query) {
        String response = botChatService.response(query.getQuery());
        return ResponseEntity.ok(Map.of("response", response));
    }

}

