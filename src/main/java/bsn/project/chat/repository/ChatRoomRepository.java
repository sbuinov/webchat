package bsn.project.chat.repository;
 
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import bsn.project.chat.domain.ChatRoom;
 
@Repository
public interface ChatRoomRepository extends CrudRepository<ChatRoom, Long> {
     
}