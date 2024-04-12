package com.chitchat.domain.DHT;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class DHTManager {
    private DHTNode localNode;
    private Map<String, DHTNode> nodes;   // 节点映射
    private Map<String, String> dataStore; // 数据存储

    public DHTManager(String nodeId, String host, int port) {
        this.nodes = new ConcurrentHashMap<>();
        this.dataStore = new ConcurrentHashMap<>();
        this.localNode = new DHTNode(nodeId, host, port, this);
        nodes.put(nodeId, localNode);
    }

    // 初始化并加入DHT网络
    public void initializeAndJoinDHTNetwork(String bootstrapNodeId) {
        DHTNode bootstrapNode = nodes.get(bootstrapNodeId);
        if (bootstrapNode != null) {
            localNode.joinNetwork(bootstrapNode);
        } else {
            // 如果没有引导节点，作为第一个节点加入
            localNode.joinNetwork(null);
        }
    }

    // 存储数据到DHT
    public void storeToDHT(String key, String value) {
        localNode.store(key, value);
    }

    // 从DHT检索数据
    public String retrieveFromDHT(String key) {
        return localNode.findValue(key);
    }

    // 获取最接近的节点
    public List<DHTNode> getClosestNodes(String key) {
        return localNode.findNode(key);
    }

    // 提供对节点映射的访问
    public Map<String, DHTNode> getNodes() {
        return this.nodes;
    }

    // 提供对数据存储的访问
    public Map<String, String> getDataStore() {
        return this.dataStore;
    }
}
