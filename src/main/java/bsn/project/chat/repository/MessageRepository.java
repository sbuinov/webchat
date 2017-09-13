package bsn.project.chat.repository;
 
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import bsn.project.chat.domain.Message;
 
@Repository
public interface MessageRepository extends CrudRepository<Message, Long> {
    
    //FIXME use @Query
    public List<Message> findByChatRoomIdOrderByTimestampDesc(long chatRoomId, Pageable pageable);

    //FIXME use @Query
    public List<Message> findByChatRoomIdAndTimestampLessThanEqualOrderByTimestampDesc
                (long chatRoomId, long timestamp, Pageable pageable);
}