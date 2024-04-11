package com.chitchat.domain.Message;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MessageService {

    // JDBC URL, username, and password of MySQL server
    private static final String URL = "jdbc:mysql://localhost:3306/chitchat";
    private static final String USER = "username";
    private static final String PASSWORD = "password";

    // JDBC variables for opening and managing connection
    private static Connection connection;
    private static Statement statement;

    // Method to add a new message to the message table
    public static void addMessage(String sender, String receiver, String messageContent, String ipAddress) {
        String insertQuery = "INSERT INTO message (sender, receiver, content, ip_address) VALUES (?, ?, ?, ?)";

        try {
            // Connect to the database
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

            // Prepare the SQL statement for insertion
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, sender);
            preparedStatement.setString(2, receiver);
            preparedStatement.setString(3, messageContent);
            preparedStatement.setString(4, ipAddress);

            // Execute the SQL statement to insert the message
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the connection
            try {
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Method to retrieve all messages from the message table
    public static List<Message> getAllMessages() {
        List<Message> messageList = new ArrayList<>();
        String selectQuery = "SELECT * FROM message";

        try {
            // Connect to the database
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

            // Execute the SQL query to retrieve messages
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectQuery);

            // Iterate through the result set and create Message objects
            while (resultSet.next()) {
                String sender = resultSet.getString("sender");
                String receiver = resultSet.getString("receiver");
                String content = resultSet.getString("content");
                LocalDateTime timestamp = resultSet.getTimestamp("timestamp").toLocalDateTime();
                String ipAddress = resultSet.getString("ip_address");
                messageList.add(new Message(sender, receiver, content, timestamp, ipAddress));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the connection
            try {
                if (connection != null) connection.close();
                if (statement != null) statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return messageList;
    }

    // Inner class to represent a message
    public static class Message {
        private String sender;
        private String receiver;
        private String content;
        private LocalDateTime timestamp;
        private String ipAddress;

        // Constructor
        public Message(String sender, String receiver, String content, LocalDateTime timestamp, String ipAddress) {
            this.sender = sender;
            this.receiver = receiver;
            this.content = content;
            this.timestamp = timestamp;
            this.ipAddress = ipAddress;
        }

        // Getters
        public String getSender() {
            return sender;
        }

        public String getReceiver() {
            return receiver;
        }

        public String getContent() {
            return content;
        }

        public LocalDateTime getTimestamp() {
            return timestamp;
        }

        public String getIpAddress() {
            return ipAddress;
        }
    }
}
