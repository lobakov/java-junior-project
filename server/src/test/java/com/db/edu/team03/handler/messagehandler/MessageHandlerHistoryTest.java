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
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class MessageHandlerHistoryTest {
    private final String ADRESS = "/127.0.0.1:10000";

    private ServerCore serverStub;
    private UserHandler userHandler;
    private MessageHandler handlerSut;
    private HistoryLogger historyLogger;
    private MessageFormatter messageFormatter;


    @BeforeEach
    public void setUp() {
        serverStub = mock(ServerCore.class);
        userHandler = mock(UserHandler.class);
        historyLogger = mock(HistoryLogger.class);

        handlerSut = new MessageHandler(serverStub, historyLogger, userHandler);

        messageFormatter = new MessageFormatter();
        when(userHandler.getNameById(any())).thenReturn("user");
    }

    @Test
    public void shouldLogMessageInHistory() throws ServerException {
        String message = "/snd message";

        handlerSut.accept(ADRESS, message);

        verify(historyLogger, times(1)).saveHistory(eq(messageFormatter.format("user", "message")));
    }

    @Test
    public void shouldReadHistory() throws ServerException {
        String message = "/hist";

        handlerSut.accept(ADRESS, message);

        verify(historyLogger, times(1)).readHistory();
    }

    @Test
    public void shouldLogErrorIsHistory() throws ServerException {
        String message = "/errorCommand body";

        handlerSut.accept(ADRESS, message);

        verify(historyLogger, times(1)).saveHistory(eq(messageFormatter.format("Server", "Unrecognized message")));
    }

    @Test
    public void shouldSendMessageWhenNicknameIsChanged() throws ServerException {
        String message = "/chid vasya";
        when(userHandler.changeUsername(any(), any())).thenReturn("vasya");

        handlerSut.accept(ADRESS, message);

        verify(historyLogger, times(1)).saveHistory(eq("user's nickname was changed to vasya"));
    }
}
