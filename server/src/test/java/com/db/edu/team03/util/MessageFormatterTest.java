package com.db.edu.team03.util;

import com.db.edu.team03.server.util.MessageFormatter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MessageFormatterTest {
    MessageFormatter formatter = new MessageFormatter();

    @Test
    public void shouldFormatMessage(){
        assertTrue(formatter.format("user", "message").matches("\\[[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}] user: message"));
    }
}
