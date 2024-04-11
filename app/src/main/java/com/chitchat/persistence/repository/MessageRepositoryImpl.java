package com.chitchat.persistence.repository;

import com.chitchat.domain.message.Message;
import com.chitchat.domain.message.MessageRepository;
import com.chitchat.persistence.entity.MessageEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class MessageRepositoryImpl implements MessageRepository {

    @Autowired
    private MessageCrudRepository messageCrudRepository;

    @Override
    public boolean existsById(Long id) {
        return messageCrudRepository.existsById(id);
    }

    @Override
    public void saveMessage(Message message) {
        MessageEntity messageEntity = new MessageEntity();
        BeanUtils.copyProperties(message, messageEntity);
        messageCrudRepository.save(messageEntity);
    }

    @Override
    public void saveMessages(List<Message> messages) {
        List<MessageEntity> messageEntities = messages.stream()
                .map(message -> {
                    MessageEntity messageEntity = new MessageEntity();
                    BeanUtils.copyProperties(message, messageEntity);
                    return messageEntity;
                })
                .toList();
        messageCrudRepository.saveAll(messageEntities);
    }

    @Override
    public List<Message> getAllMessages() {
        Iterable<MessageEntity> iterable = messageCrudRepository.findAll();
        return convertToMessages(iterable);
    }

    @Override
    public List<Message> getMessagesByIds(List<Long> ids) {
        Iterable<MessageEntity> iterable = messageCrudRepository.findAllById(ids);
        return convertToMessages(iterable);
    }

    private List<Message> convertToMessages(Iterable<MessageEntity> iterable) {
        Iterator<MessageEntity> iterator = iterable.iterator();
        List<Message> messages = new ArrayList<>();
        while (iterator.hasNext()) {
            MessageEntity messageEntity = iterator.next();
            Message message = new Message();
            BeanUtils.copyProperties(messageEntity, message);
            messages.add(message);
        }
        return messages;
    }
}
