package bsn.project.chat.web;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import bsn.project.chat.domain.ChatRoom;
import bsn.project.chat.domain.Message;
import bsn.project.chat.repository.ChatRoomRepository;
import bsn.project.chat.repository.MessageRepository;

@Controller
public class WebSocketController {

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private MessageRepository messageRepository;

	@MessageMapping("/newchatroom")
	@SendTo("/topic/chatroom")
	public ChatRoom processNewChatRoom(@Payload MessageRequest request) throws Exception {
		ChatRoom chatRoom = new ChatRoom(request.getContent());
		return chatRoomRepository.save(chatRoom);
	}

    @MessageMapping("/newmessage/{chatRoomId}")
    @SendTo("/topic/message")
    public Message processNewMessage(@Payload MessageRequest request, @DestinationVariable 
                long chatRoomId, Principal principal) throws Exception {
        Message message = new Message();
        message.setChatRoomId(chatRoomId);
        message.setContent(request.getContent());
        message.setUserName(principal.getName());
        return messageRepository.save(message);
    }

	@MessageExceptionHandler
    @SendToUser("/topic/errors")
    public String handleException(Exception exception) {
        return exception.getMessage();
    }

}
