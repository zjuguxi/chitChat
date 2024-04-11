package com.chitchat.persistence.repository;

import com.chitchat.domain.message.Message;
import com.chitchat.domain.message.MessageRepository;
import com.chitchat.persistence.po.MessagePo;
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
    public boolean existsById(String id) {
        return messageCrudRepository.existsById(id);
    }

    @Override
    public void saveMessage(Message message) {
        MessagePo messagePo = new MessagePo();
        BeanUtils.copyProperties(message, messagePo);
        messageCrudRepository.save(messagePo);
    }

    @Override
    public void saveMessages(List<Message> messages) {
        List<MessagePo> messageEntities = messages.stream()
                .map(message -> {
                    MessagePo messagePo = new MessagePo();
                    BeanUtils.copyProperties(message, messagePo);
                    return messagePo;
                })
                .toList();
        messageCrudRepository.saveAll(messageEntities);
    }

    @Override
    public List<Message> getAllMessages() {
        Iterable<MessagePo> iterable = messageCrudRepository.findAll();
        return convertToMessages(iterable);
    }

    @Override
    public List<Message> getMessagesByIds(List<String> ids) {
        Iterable<MessagePo> iterable = messageCrudRepository.findAllById(ids);
        return convertToMessages(iterable);
    }

    private List<Message> convertToMessages(Iterable<MessagePo> iterable) {
        Iterator<MessagePo> iterator = iterable.iterator();
        List<Message> messages = new ArrayList<>();
        while (iterator.hasNext()) {
            MessagePo messagePo = iterator.next();
            Message message = new Message();
            BeanUtils.copyProperties(messagePo, message);
            messages.add(message);
        }
        return messages;
    }
}
