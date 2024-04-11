package com.chitchat.persistence.repository;

import com.chitchat.persistence.po.MessagePo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageCrudRepository extends CrudRepository<MessagePo, String> {

    @Query(nativeQuery = true, value = "select id from message")
    List<String> getAllId();
}
