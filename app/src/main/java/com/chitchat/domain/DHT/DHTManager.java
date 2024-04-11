package com.chitchat.domain.DHT;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DHTManager {
    private DHTNode localNode;
    private Map<String, DHTNode> nodes;
    public DHTManager() {
        this.nodes = new HashMap<>();
    }

    // Initiallization
    public void initializeDHTNetwork(String nodeId, String host, int port) {
        this.localNode = new DHTNode(nodeId, host, port);
        this.localNode.joinNetwork(null); // Bootstrap node as a parameter
        nodes.put(nodeId, localNode);
    }

    // Join DHT
    public void joinDHTNetwork(String nodeId, String bootstrapNodeId) {
        DHTNode bootstrapNode = nodes.get(bootstrapNodeId);
        if (bootstrapNode != null) {
            localNode.joinNetwork(bootstrapNode);
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

    private class DHTNode {
        private String nodeId;
        private String host;
        private int port;

        public DHTNode(String nodeId, String host, int port) {
            this.nodeId = nodeId;
            this.host = host;
            this.port = port;
        }

        public void joinNetwork(DHTNode bootstrapNode) {

        }

        public void store(String key, String value) {

        }

        public String findValue(String key) {

            return null;
        }

        public List<DHTNode> findNode(String key) {
            return null;
        }
    }
}
