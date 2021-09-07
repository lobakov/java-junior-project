package com.db.edu.team03.handler;

import com.db.edu.team03.server.handler.UserHandler;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserHendlerTest {
    UserHandler userHandler = new UserHandler();
    private final String id = "1234";

    @Test
    public void shouldAddNameInMap() {
        userHandler.accept(id);
        assertTrue(userHandler.getUsers().containsKey(id));
    }

    @Test
    public void shouldChangeNametoUser() {
        String name = "Vasya";
        userHandler.accept(id);
        userHandler.changeUsername(id,name);
        assertTrue(userHandler.getUsers().containsValue(name));
    }

    @Test
    public void shouldReturnNameFromId() {
        userHandler.accept(id);
        assertTrue(userHandler.getNameById(id).equals(UserHandler.getDefaultUsername()));
    }

    @Test
    public void shouldRemoveFromUsersMapById() {
        String id2 = "12345";
        userHandler.accept(id);
        userHandler.accept(id2);
        userHandler.removeUserByAddress(id);
        assertFalse(userHandler.getUsers().containsKey(id));
    }

    @Test
    public void shouldReturnErrorMsgIfNameIncorrect() {
        userHandler.accept(id);
        String msg = "Your username shouldn't have whitespace symbols";
        assertTrue(userHandler.changeUsername(id, "sdf weft").equals(msg));
    }
    @Test
    public void shouldReturnErrorMsgIfNameNotUnique() {
        userHandler.accept(id);
        userHandler.changeUsername(id, "Vasya");
        String msg = "Server have this username yet. Use unique username";
        assertTrue(userHandler.changeUsername(id, "Vasya").equals(msg));
    }
}
