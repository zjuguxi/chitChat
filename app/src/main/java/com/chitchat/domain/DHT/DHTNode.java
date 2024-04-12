package com.chitchat.domain.DHT;

import java.util.ArrayList;
import java.util.List;

public class DHTNode {
    private String nodeId;
    private String host;
    private int port;
    private DHTManager manager;

    public DHTNode(String nodeId, String host, int port, DHTManager manager) {
        this.nodeId = nodeId;
        this.host = host;
        this.port = port;
        this.manager = manager;
        // 存储本节点信息到数据库
        DHTUtils.storeNode(nodeId, host, port);
    }

    public void joinNetwork(DHTNode bootstrapNode) {
        if (bootstrapNode != null) {
            // 获取最接近的节点列表
            List<DHTNode> closestNodes = bootstrapNode.findNode(nodeId);
            for (DHTNode node : closestNodes) {
                manager.getNodes().putIfAbsent(node.nodeId, node);
            }
            System.out.println("Node " + nodeId + " joined the network using bootstrap node " + bootstrapNode.nodeId);
        } else {
            System.out.println("Node " + nodeId + " joined as the first node in the network.");
        }
    }

    public void store(String key, String value) {
        manager.getDataStore().put(key, value);
        System.out.println("Key: " + key + " stored with value: " + value);
    }

    public String findValue(String key) {
        String value = manager.getDataStore().get(key);
        if (value != null) {
            System.out.println("Local value for key: " + key + " is " + value);
            return value;
        }
        System.out.println("Value for key: " + key + " not found locally. Searching in other nodes...");
        return null;
    }

    public List<DHTNode> findNode(String targetNodeId) {
        // 简化的查找逻辑，返回所有已知节点
        return new ArrayList<>(manager.getNodes().values());
    }
}
