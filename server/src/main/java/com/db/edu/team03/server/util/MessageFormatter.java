package com.db.edu.team03.server.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * MessageFormatter - class that handles formatting for chat messages
 */
public class MessageFormatter {

    /**
     * The only public api method that formats messages
     *
     * @param name    - user name of a user sending this message
     * @param message - message body of a message sent by user
     * @return formatted message suitable for sending to chat
     */
    public String format(String name, String message) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);

        return String.format("[%s] %s: %s", formattedDateTime, name, message);
    }
}
