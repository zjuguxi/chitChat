package com.chitchat.domain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.chitchat.domain.DHT.DHTManager;

@SpringBootApplication
public class ChitChatNode {

    public static void main(String[] args) {
        SpringApplication.run(ChitChatNode.class, args);
    }

    @Bean
    public DHTManager dhtManager() {
        // Initialize DHTManager
        DHTManager manager = new DHTManager();
        manager.initializeDHTNetwork("nodeId1", "localhost", 8080);
        return manager;
    }


}
