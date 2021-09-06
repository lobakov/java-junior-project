package com.db.edu.team03.server.handler;

public interface Handler {

    default void accept(String data) {

    };

    default void accept(String id, String message) {

    };
}
