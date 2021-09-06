package com.db.edu.team03.server.handler;

import com.db.edu.team03.server.exception.ServerException;

public interface Handler {

    void accept(String address, String message) throws ServerException;
}
