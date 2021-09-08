package com.db.edu.team03.handler.messagehandler;

import com.db.edu.team03.server.core.ServerCore;
import com.db.edu.team03.server.handler.MessageHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

public class MessageHandlerCommandTest {
    private ServerCore serverStub;
    private MessageHandler handlerSut;

    @BeforeEach
    public void setUp() {
        serverStub = mock(ServerCore.class);
//        handlerSut = new MessageHandler(serverStub);
    }

    @Test
    public void shouldGetErrorWhenNoMessageProvided() {

    }




}
