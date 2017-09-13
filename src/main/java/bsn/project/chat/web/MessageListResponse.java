package bsn.project.chat.web;

import java.util.List;

import bsn.project.chat.domain.Message;

public class MessageListResponse {

    private String chatRoomName;
    private List<Message> messages;
    private Message nextMessage;
    
    public String getChatRoomName() {
        return chatRoomName;
    }

    public void setChatRoomName(String chatRoomName) {
        this.chatRoomName = chatRoomName;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public Message getNextMessage() {
        return nextMessage;
    }

    public void setNextMessage(Message nextMessage) {
        this.nextMessage = nextMessage;
    }

}
