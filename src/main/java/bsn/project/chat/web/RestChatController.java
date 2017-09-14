package bsn.project.chat.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bsn.project.chat.domain.ChatRoom;
import bsn.project.chat.domain.Message;
import bsn.project.chat.repository.ChatRoomRepository;
import bsn.project.chat.repository.MessageRepository;

@RestController
@RequestMapping("/api")
public class RestChatController {

    private static final int PAGE_SIZE = 15;
    
    @Autowired
    private MessageRepository messageRepository;
    
    @Autowired
    private ChatRoomRepository chatRoomRepository;
    
    @RequestMapping(value = "/messages/{chatRoomId}", method = RequestMethod.GET)
    public MessageListResponse messages(@PathVariable("chatRoomId") long chatRoomId, 
            @RequestParam(name = "fromTimestamp", required=false) Long fromTimestamp) {
        ChatRoom chatRoom = chatRoomRepository.findOne(chatRoomId);
        if (chatRoom == null) {
            throw new IllegalStateException("Chat room not found.");
        }
        
        Pageable pageable = new PageRequest(0, PAGE_SIZE + 1, Direction.DESC, "timestamp");
        List<Message> messages;
        if (fromTimestamp == null) {
            messages = messageRepository.findByChatRoomIdOrderByTimestampDesc(chatRoomId, pageable);
        } else {
            messages = messageRepository.findByChatRoomIdAndTimestampLessThanEqualOrderByTimestampDesc(chatRoomId, fromTimestamp, pageable);
        }
        Message lastMessage = null;
        if (messages.size() == PAGE_SIZE + 1) {
            lastMessage = messages.remove(PAGE_SIZE);
        }
        MessageListResponse response = new MessageListResponse();
        response.setChatRoomName(chatRoom.getName());
        response.setMessages(messages);
        response.setNextMessage(lastMessage);
        return response;
    }
    

}
