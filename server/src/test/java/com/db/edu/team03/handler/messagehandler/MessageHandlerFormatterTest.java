package com.db.edu.team03.handler.messagehandler;

import com.db.edu.team03.server.core.ServerCore;
import com.db.edu.team03.server.exception.ServerException;
import com.db.edu.team03.server.handler.HistoryLogger;
import com.db.edu.team03.server.handler.MessageHandler;
import com.db.edu.team03.server.handler.UserHandler;
import com.db.edu.team03.server.util.MessageFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class MessageHandlerFormatterTest {
    private final String ADRESS = "/127.0.0.1:10000";

    private ServerCore serverStub;
    private MessageHandler handlerSut;
    private MessageFormatter messageFormatter;


    @BeforeEach
    public void setUp() {
        serverStub = mock(ServerCore.class);
        messageFormatter = mock(MessageFormatter.class);


        handlerSut = new MessageHandler(serverStub, mock(HistoryLogger.class), mock(UserHandler.class), messageFormatter);
    }

    @Test
    public void shouldCallFormatterWhenLogMessage() throws ServerException {
        String message = "/snd message";

        handlerSut.accept(ADRESS, message);

        verify(messageFormatter, times(1)).format(any(), eq("message"));
    }

    @Test
    public void shouldCallFormatterErrorMessageSent() throws ServerException {
        String message = "/errorCommand body";

        handlerSut.accept(ADRESS, message);

        verify(messageFormatter, times(1)).format(eq("Server"), eq("Unrecognized message"));
    }
}
