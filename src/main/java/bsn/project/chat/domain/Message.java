package bsn.project.chat.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "message")
public class Message implements Serializable {
 
    private static final long serialVersionUID = -54703339430173697L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
 
    @Column(name = "chat_room_id")
    private long chatRoomId;

    @Column(name = "content")
    private String content;

    @Column(name = "username")
    private String userName;
    
    @Column(name = "created_timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern="d MMMM yyyy HH:mm:ss")
    private Date created;

    @Column(name = "timestamp")
    private long timestamp;
    
    public Message() {
        this.created = new Date();
        //TODO generate unique timestamp using java.util.concurrent
        this.timestamp = System.currentTimeMillis();
    }

    public Message(long chatRoomId, String userName, String content) {
        this();
        this.chatRoomId = chatRoomId;
        this.userName = userName;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getChatRoomId() {
        return chatRoomId;
    }

    public void setChatRoomId(long chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
    
}