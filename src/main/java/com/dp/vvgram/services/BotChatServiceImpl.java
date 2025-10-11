package com.dp.vvgram.services;

import com.dp.vvgram.models.BotChat;
import com.dp.vvgram.models.BotConversation;
import com.dp.vvgram.models.User;
import com.dp.vvgram.repositories.BotChatRepository;
import com.dp.vvgram.repositories.BotConversationRepository;
import com.dp.vvgram.repositories.UserRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class BotChatServiceImpl implements BotChatService {
    private final ChatClient chatClient;
    private final BotChatRepository botChatRepository;
    private final UserRepository userRepository;
    private final BotConversationRepository botConversationRepository;

    private UserDetails getUserDetails() {
        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public BotChatServiceImpl(ChatClient chatClient, BotChatRepository botChatRepository,
                              UserRepository userRepository, BotConversationRepository botConversationRepository) {
        this.botConversationRepository = botConversationRepository;
        this.userRepository = userRepository;
        this.botChatRepository = botChatRepository;
        this.chatClient = chatClient;
    }

    @Override
    public String response(String query) {
        UserDetails userDetails = getUserDetails();
        Optional<User> optionalUser = userRepository.findByUsername(userDetails.getUsername());
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        User user = optionalUser.get();
        BotChat botChat = null;
        if (user.getBotChat() == null) {
            botChat = createBotChat();
        }
        else {
            botChat = user.getBotChat();
        }
        user.setBotChat(botChat);

        String response = chatClient.prompt(query).call().content();

        BotConversation botConversation = new BotConversation();
        botConversation.setBotChat(botChat);
        botConversation.setQuery(query);
        botConversation.setResponse(response);
        botChat.getConversation().add(botConversation);
        botChat.setUser(user);

        botChatRepository.save(botChat);
        userRepository.save(user);
        botConversationRepository.save(botConversation);
        return response;
    }

    private BotChat createBotChat() {
        BotChat botChat = new BotChat();
        botChat.setConversation(new ArrayList<>());
        return botChat;
    }
}
