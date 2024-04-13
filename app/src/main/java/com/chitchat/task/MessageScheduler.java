package com.chitchat.task;

import com.chitchat.domain.message.Message;
import com.chitchat.domain.message.MessageGrpcClient;
import com.chitchat.domain.message.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
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
        for (String serverAddress : serverAddresses) {
            CompletableFuture.runAsync(() -> {
                try {
                    log.info("Start sync messages with server {}", serverAddress);
                    syncMessages(serverAddress);
                    log.info("Sync messages with server {} successful", serverAddress);
                } catch (Exception e) {
                    log.error(String.format("Sync messages with server %s failed", serverAddress), e);
                }
            });
        }
    }

    private void syncMessages(String serverAddress) {
        List<String> existedIds = messageService.getAllMessageIds();
        List<String> fetchedIds = new ArrayList<>(messageGrpcClient.fetchMessageIds(serverAddress));
        fetchedIds.removeAll(existedIds);
        if (!fetchedIds.isEmpty()) {
            List<Message> diffMessages = messageGrpcClient.pullMessages(serverAddress, fetchedIds);
            messageService.saveMessages(diffMessages);
        }
    }
}
