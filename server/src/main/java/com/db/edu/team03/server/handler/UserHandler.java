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
    private Object monitor = new Object();
    /**
     * Associates new user with default user name by his address;
     *
     * @param address
     */
    public void accept(String address) {
        synchronized(monitor){
            users.putIfAbsent(address, DEFAULT_USERNAME);
        }
    }

    /**
     * Changes user name to the one chosen by user
     *
     * @param address  - ip address and port of user willing to change name
     * @param username - new name received by user
     */
    public String changeUsername(String address, String username) {
        if (checkUsernameCorrect(username)) {
            return "Your username shouldn't have whitespace symbols";
        } else if (checkUniqueUsername(username)) {
            return "Server have this username yet. Use unique username";
        } else {
            synchronized(monitor){
                users.put(address, username);
            }
            return username;
        }
    }

    private boolean checkUsernameCorrect(String username) {
        char[] notNeedSymbols = {'\u0020', '\u0009', '\u000C', '\r', '\n'};
        boolean res = false;
        for (char ch : notNeedSymbols) {
            if (username.indexOf(ch) != -1) {
                res = true;
            }
        }
        return res;
    }

    private boolean checkUniqueUsername(String username) {
        return users.containsValue(username);
    }

    /**
     * Gets name associated with user ip and port
     *
     * @param address - user's ip and port
     * @return - returns name currently associated with address
     */
    public String getNameById(String address) {
        return users.get(address);
    }

    /**
     * Remove user from map users by his ip and port
     *
     * @param address - user's ip and port
     */
    public void removeUserByAddress(String address) {
        synchronized(monitor){
            users.remove(address);
        }
    }

    public Map<String, String> getUsers() {
        return users;
    }

    public static String getDefaultUsername() {
        return DEFAULT_USERNAME;
    }
}
