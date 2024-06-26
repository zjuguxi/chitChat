package com.chitchat.domain.message;

import com.chitchat.util.IpAddressUtils;
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

        if (message.getContent() == null || message.getContent().trim().isEmpty()) {
            log.info("Message content is empty");
            return;
        }

        if (message.getId() == null) {
            long timestamp = System.currentTimeMillis();
            message.setId(timestamp + "_" + IpAddressUtils.getFormattedIpAddress());
            message.setSender(IpAddressUtils.getIpAddress());
            message.setSendTime(timestamp);
        }

        if (messageRepository.existsById(message.getId())) {
            log.info("Message {} already existed", message.getId());
            return;
        }

        messageRepository.saveMessage(message);

        messageGrpcClient.pushMessage(message);
    }

    @Override
    public void saveMessages(List<Message> messages) {

        if (messages == null || messages.isEmpty()) {
            log.info("Message list is empty");
            return;
        }

        messageRepository.saveMessages(messages);
    }

    @Override
    public List<Message> getAllMessages() {
        return messageRepository.getAllMessages();
    }

    @Override
    public List<String> getAllMessageIds() {
        return messageRepository.getAllMessageIds();
    }

    @Override
    public List<Message> getMessagesByIds(List<String> ids) {
        return messageRepository.getMessagesByIds(ids);
    }
}
