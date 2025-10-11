package com.dp.vvgram.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "bot_conversations")
@Getter @Setter
public class BotConversation extends BaseModel{
    @ManyToOne
    private BotChat botChat;
    @Lob
    private String query;
    @Lob
    private String response;
}
