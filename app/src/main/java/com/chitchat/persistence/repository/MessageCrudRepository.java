package com.chitchat.persistence.repository;

import com.chitchat.persistence.entity.MessageEntity;
import org.springframework.data.repository.CrudRepository;

public interface MessageCrudRepository extends CrudRepository<MessageEntity, Long> {
}
