package com.db.edu.team03.server.handler;

import java.util.HashMap;
import java.util.Map;

/**
 * UserHandler - class that handles logic of storing and updating user names associated with their ip address and port
 * for the current session.
 */
public class UserHandler {

    private static final String DEFAULT_USERNAME = "user";

    private Map<String, String> users = new HashMap<>();

    /**
     * Associates new user with default user name by his address;
     * @param address
     */
    public void accept(String address) {
        users.putIfAbsent(address, DEFAULT_USERNAME);
    }

    /**
     * Changes user name to the one chosen by user
     * @param address - ip address and port of user willing to change name
     * @param username - new name received by user
     */
    public void changeUsername(String address, String username) {
        users.put(address, username);
    }

    /**
     * Gets name associated with user ip and port
     * @param address - user's ip and port
     * @return - returns name currently associated with address
     */
    public String getNameByAddress(String address) {
        return users.get(address);
    }
}
