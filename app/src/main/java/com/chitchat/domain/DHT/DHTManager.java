package com.chitchat.domain.DHT;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class DHTManager {
    private DHTNode localNode;
    private Map<String, DHTNode> nodes;
    private Map<String, String> dataStore;

    public DHTManager(String nodeId, String host, int port) {
        this.nodes = new ConcurrentHashMap<>();
        this.dataStore = new ConcurrentHashMap<>();
        this.localNode = new DHTNode(nodeId, host, port, this);
        nodes.put(nodeId, localNode);
    }

    // Initialize and join DHTNetwork
    public void initializeAndJoinDHTNetwork(String bootstrapNodeId) {
        DHTNode bootstrapNode = nodes.get(bootstrapNodeId);
        if (bootstrapNode != null) {
            localNode.joinNetwork(bootstrapNode);
        } else {
            localNode.joinNetwork(null);
        }
    }

    public void storeToDHT(String key, String value) {
        localNode.store(key, value);
    }

    public String retrieveFromDHT(String key) {
        return localNode.findValue(key);
    }

    public List<DHTNode> getClosestNodes(String key) {
        return localNode.findNode(key);
    }

    public Map<String, DHTNode> getNodes() {
        return this.nodes;
    }

    public Map<String, String> getDataStore() {
        return this.dataStore;
    }
}
