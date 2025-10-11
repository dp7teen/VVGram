package com.dp.vvgram.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity(name = "bot_chats")
@Getter @Setter
public class BotChat extends BaseModel{
    @OneToMany(mappedBy = "botChat", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<BotConversation> conversation;

    @OneToOne @JoinColumn(name = "user_id", unique = true)
    private User user;
}
