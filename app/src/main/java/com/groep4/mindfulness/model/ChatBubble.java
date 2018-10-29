package com.groep4.mindfulness.model;

public class ChatBubble {
    private String content;
    public boolean myMessage;

    public ChatBubble(String content, boolean myMessage) {
        this.content = content;
        this.myMessage = myMessage;
    }

    public String getContent() {
        return content;
    }

    public boolean isMyMessage() {
        return myMessage;
    }
}
