package com.db.edu.team03.handler.messagehandler;

import com.db.edu.team03.server.core.ServerCore;
import com.db.edu.team03.server.exception.ServerException;
import com.db.edu.team03.server.handler.HistoryLogger;
import com.db.edu.team03.server.handler.MessageHandler;
import com.db.edu.team03.server.handler.UserHandler;
import com.db.edu.team03.server.util.MessageFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class MessageHandlerSendMessageTest {
    private final String ADRESS = "/127.0.0.1:10000";

    private ServerCore serverStub;
    private UserHandler userHandler;
    private MessageHandler handlerSut;
    private MessageFormatter messageFormatter;


    @BeforeEach
    public void setUp() {
        serverStub = mock(ServerCore.class);
        userHandler = mock(UserHandler.class);

        messageFormatter = new MessageFormatter();


        handlerSut = new MessageHandler(serverStub, mock(HistoryLogger.class), userHandler, messageFormatter);

        when(userHandler.getNameById(any())).thenReturn("user");
    }

    @Test
    public void shouldSendMessageToAllUsers() throws ServerException {
        String message = "/snd message";

        handlerSut.accept(ADRESS, message);

        verify(serverStub, times(1)).sendAll(eq(messageFormatter.format("user", "message")));
    }

    @Test
    public void shouldSendHistoryToUser() throws ServerException {
        String message = "/hist";

        handlerSut.accept(ADRESS, message);

        verify(serverStub, times(1)).sendToUser(eq(ADRESS), any());
    }

    @Test
    public void shouldSendMessageWhenNicknameIsChanged() throws ServerException {
        String message = "/chid vasya";
        when(userHandler.changeUsername(any(), any())).thenReturn("vasya");

        handlerSut.accept(ADRESS, message);

        verify(serverStub, times(1)).sendAll(eq("user's nickname was changed to vasya"));
    }

    @Test
    public void shouldSendMessageWhenNicknameIsBusy() throws ServerException {
        String message = "/chid vasya";
        when(userHandler.changeUsername(any(), any())).thenReturn("Server have this username yet. Use unique username");

        handlerSut.accept(ADRESS, message);

        verify(serverStub, times(1)).sendToUser(eq(ADRESS), eq("Server have this username yet. Use unique username"));
    }

    @Test
    public void shouldSendMessageWhenNicknameHasWhiteSpaces() throws ServerException {
        String message = "/chid vasya pupkin";
        when(userHandler.changeUsername(any(), any())).thenReturn("Your username shouldn't have whitespace symbols");

        handlerSut.accept(ADRESS, message);

        verify(serverStub, times(1)).sendToUser(eq(ADRESS), eq("Your username shouldn't have whitespace symbols"));
    }

    @Test
    public void shouldSendErrorMessage() throws ServerException {
        String message = "/errorCommand body";

        handlerSut.accept(ADRESS, message);

        verify(serverStub, times(1)).sendToUser(eq(ADRESS), eq(messageFormatter.format("Server","Unrecognized message")));
    }
}
