package com.chitchat.task;

import com.chitchat.domain.message.Message;
import com.chitchat.domain.message.MessageGrpcClient;
import com.chitchat.domain.message.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MessageScheduler {

    @Value("${grpc.client.test.address}")
    private List<String> serverAddresses;

    @Autowired
    private MessageService messageService;

    @Autowired
    private MessageGrpcClient messageGrpcClient;

    @Scheduled(cron = "0 0/1 * * * ?")
    public void syncMessagesTask() {
        List<String> existedIds = messageService.getAllMessageIds();
        for (String serverAddress : serverAddresses) {
            List<String> diffIds = messageGrpcClient.fetchMessageIds(serverAddress);
            diffIds.removeAll(existedIds);
            List<Message> diffMessages = messageGrpcClient.pullMessages(serverAddress, diffIds);
            messageService.saveMessages(diffMessages);
            existedIds.addAll(diffIds);
        }
    }
}
