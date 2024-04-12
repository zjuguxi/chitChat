package com.chitchat.domain.DHT;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class DHTNode implements Runnable {
    private String nodeId;
    private String host;
    private int port;
    private ServerSocket serverSocket;
    private DHTManager manager;
    private volatile boolean running = true;
    private Map<String, String> dataStore = new ConcurrentHashMap<>();

    public DHTNode(String nodeId, String host, int port, DHTManager manager) throws IOException {
        this.nodeId = nodeId;
        this.host = host;
        this.port = port;
        this.manager = manager;
        this.serverSocket = new ServerSocket(port);
        new Thread(this).start();
    }

    @Override
    public void run() {
        while (running) {
            try (Socket socket = serverSocket.accept();
                 ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                 ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream())) {

                Object request = inputStream.readObject();
                if (request instanceof String) {
                    String key = (String) request;
                    String value = dataStore.get(key);
                    if (value != null) {
                        outputStream.writeObject(value);
                    } else {
                        outputStream.writeObject("Not found");
                    }
                } else if (request instanceof Map.Entry) {
                    Map.Entry<String, String> entry = (Map.Entry<String, String>) request;
                    dataStore.put(entry.getKey(), entry.getValue());
                    outputStream.writeObject("Stored");
                }
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error handling client request: " + e.getMessage());
            }
        }
    }

    public void stop() throws IOException {
        running = false;
        serverSocket.close();
    }

    public void store(String key, String value) throws IOException {
        // We store data locally for simplicity
        dataStore.put(key, value);

        // Also try to replicate this data in the closest nodes
        replicateData(key, value);
    }

    private void replicateData(String key, String value) throws IOException {
        // In a real system, you would find the closest nodes and replicate the data there
        // For now, let's just log the replication action
        System.out.println("Replicating data to closest nodes...");
    }

    public String findValue(String key) throws IOException {
        return dataStore.get(key);
    }
}
