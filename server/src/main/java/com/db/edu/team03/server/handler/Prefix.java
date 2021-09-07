package com.db.edu.team03.server.handler;

public enum Prefix {
    SEND("/snd"),
    CHID("/chid"),
    HIST("/hist");

    public final String value;

    Prefix(String value) {
        this.value = value;
    }
}
