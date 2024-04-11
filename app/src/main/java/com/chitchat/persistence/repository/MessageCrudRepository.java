package com.chitchat.persistence.repository;

import com.chitchat.persistence.po.MessagePo;
import org.springframework.data.repository.CrudRepository;

public interface MessageCrudRepository extends CrudRepository<MessagePo, String> {
}
