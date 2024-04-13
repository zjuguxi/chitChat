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
        DHTManager manager = new DHTManager("nodeId1", "localhost", 8080);
        manager.initializeAndJoinDHTNetwork(null);
        return manager;
    }
}
