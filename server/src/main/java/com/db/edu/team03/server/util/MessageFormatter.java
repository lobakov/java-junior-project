package com.db.edu.team03.server.util;

import java.time.LocalTime;

public class MessageFormatter {

    public String format(String id, String message) {
        String time = LocalTime.now().toString();

        return String.format("[%s] %s: %s", time, id, message);
    }
}
