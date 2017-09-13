package bsn.project.chat.repository;
 
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import bsn.project.chat.domain.User;
 
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    public User findByUserName(String username);
     
}