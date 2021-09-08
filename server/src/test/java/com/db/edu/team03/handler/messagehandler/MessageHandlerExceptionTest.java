package com.db.edu.team03.handler.messagehandler;

import com.db.edu.team03.server.core.ServerCore;
import com.db.edu.team03.server.exception.ServerException;
import com.db.edu.team03.server.handler.*;
import com.db.edu.team03.server.util.MessageFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class MessageHandlerExceptionTest {

    private ServerCore serverStub;
    private MessageHandler handlerSut;

    @BeforeEach
    public void setUp() {
        serverStub = mock(ServerCore.class);

        HistoryLogger historyLogger = mock(HistoryLogger.class);
        UserHandler userHandler = mock(UserHandler.class);
        handlerSut = new MessageHandler(serverStub, historyLogger, userHandler);
    }

    @Test
    public void shouldGetErrorWhenNoMessageProvided() {
        String id = "id";
        String noMessage = null;

        assertThrows(
                ServerException.class,
                () -> handlerSut.accept(id, noMessage)
        );
    }

    @Test
    public void shouldGetErrorWhenNoIdProvided() {
        String noId = null;
        String message = "message";

        assertThrows(
                ServerException.class,
                () -> handlerSut.accept(noId, message)
        );
    }

    @Test
    public void shouldGetErrorWhenEmptyMessageProvided() {
        String id = "id";
        String emptyMessage = "";

        assertThrows(
                ServerException.class,
                () -> handlerSut.accept(id, emptyMessage)
        );
    }

    @Test
    public void shouldGetErrorWhenEmptyIdProvided() {
        String emptyId = "";
        String message = "message";

        assertThrows(
                ServerException.class,
                () -> handlerSut.accept(emptyId, message)
        );
    }

    @Test
    public void shouldSendMessageToAllWhenChatMessageReceived() throws ServerException {
        String id = "/127.0.0.1:10000";
        String message = "/snd message";

        handlerSut.accept(id, message);

        verify(serverStub).sendAll(any());
    }
}
