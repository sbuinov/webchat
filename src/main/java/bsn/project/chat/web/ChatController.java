package bsn.project.chat.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import bsn.project.chat.domain.ChatRoom;
import bsn.project.chat.repository.ChatRoomRepository;

@Controller
@RequestMapping("/")
public class ChatController {

    @Autowired
    private ChatRoomRepository chatRoomRepository;
    
    @RequestMapping(value ="/", method = RequestMethod.GET)
    public ModelAndView show() {
        Iterable<ChatRoom> chatRooms = chatRoomRepository.findAll();
        ModelAndView modelAndView = new ModelAndView("chat");
        modelAndView.addObject("chatRooms", chatRooms);
        return modelAndView;
    }
    
}
