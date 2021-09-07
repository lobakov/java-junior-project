package com.db.edu.team03.handler;

import com.db.edu.team03.server.handler.UserHandler;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserHendlerTest {
    UserHandler userHandler = new UserHandler();

    @Test
    public void shouldAddNameInMap() {
        String id = "1234";
        userHandler.accept(id);
        assertTrue(userHandler.getUsers().containsKey(id));
    }

    @Test
    public void shouldChangeNametoUser() {
        String id = "1234";
        String name = "Vasya";
        userHandler.accept(id);
        userHandler.changeUsername(id,name);
        assertTrue(userHandler.getUsers().containsValue(name));
    }

    @Test
    public void shouldReturnNameFromId() {
        String id = "1234";
        userHandler.accept(id);
        assertTrue(userHandler.getNameById(id).equals(UserHandler.getDefaultUsername()));
    }
}
