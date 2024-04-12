package com.chitchat.domain.DHT;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class DHTManager {
    private DHTNode localNode;
    private Map<String, DHTNode> nodes;  // 假设节点ID与节点的映射
    private Map<String, String> dataStore;  // 本地数据存储

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
            // 如果没有引导节点，视为网络中的第一个节点
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

    // 简化的DHT节点实现
    private class DHTNode {
        private String nodeId;
        private String host;
        private int port;
        private DHTManager manager;

        public DHTNode(String nodeId, String host, int port, DHTManager manager) {
            this.nodeId = nodeId;
            this.host = host;
            this.port = port;
            this.manager = manager;
        }

        public void joinNetwork(DHTNode bootstrapNode) {
            if (bootstrapNode != null) {
                // 如果存在引导节点，从它那里获取网络信息
                List<DHTNode> closestNodes = bootstrapNode.findNode(nodeId);
                for (DHTNode node : closestNodes) {
                    manager.nodes.putIfAbsent(node.nodeId, node);
                }
            }
            // 模拟"加入"网络
            System.out.println("Node " + nodeId + " joined the network.");
        }

        public void store(String key, String value) {
            // 暂时将所有数据存储在本地节点，简化模型
            manager.dataStore.put(key, value);
            System.out.println("Stored key: " + key + " with value: " + value);
        }

        public String findValue(String key) {
            // 检查本地存储
            String value = manager.dataStore.get(key);
            if (value != null) {
                System.out.println("Value for key: " + key + " found locally: " + value);
                return value;
            }
            // 模拟从其他节点获取值
            for (DHTNode node : manager.nodes.values()) {
                if (node != this) { // 简化，不考虑实际网络请求
                    value = node.findValue(key);
                    if (value != null) {
                        return value;
                    }
                }
            }
            System.out.println("Value for key: " + key + " not found.");
            return null;
        }

        public List<DHTNode> findNode(String targetNodeId) {
            // 返回最近的几个节点，简化处理
            return new ArrayList<>(manager.nodes.values());
        }
    }
}
