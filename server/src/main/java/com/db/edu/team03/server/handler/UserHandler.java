package com.db.edu.team03.server.handler;

import java.util.HashMap;
import java.util.Map;

public class UserHandler {

    private static final String DEFAULT_USERNAME = "user";

    private Map<String, String> users = new HashMap<>();

    public void accept(String id) {
        users.putIfAbsent(id, DEFAULT_USERNAME);
    }

    public void changeUsername(String id, String username) {
        users.put(id, username);
    }

    public String getIdByAddress(String address) {
        return users.get(address);
    }
}
