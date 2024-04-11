package com.chitchat.domain.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private MessageGrpcClient messageGrpcClient;

    @Override
    public void saveMessage(Message message) {

        if (messageRepository.existsById(message.getId())) {
            log.info("Message with id {} already existed", message.getId());
            return;
        }

        messageRepository.saveMessage(message);

        messageGrpcClient.pushMessage(message);
    }

    @Override
    public void saveMessages(List<Message> messages) {
        messageRepository.saveMessages(messages);
    }

    @Override
    public List<Message> getAllMessages() {
        return messageRepository.getAllMessages();
    }

    @Override
    public List<Message> getMessagesByIds(List<Long> ids) {
        return messageRepository.getMessagesByIds(ids);
    }
}
