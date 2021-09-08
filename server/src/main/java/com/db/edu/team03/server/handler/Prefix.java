package com.db.edu.team03.server.handler;

public enum Prefix {
    SEND("/snd"),
    CHID("/chid"),
    HIST("/hist"),
    CHROOM("/chroom");

    public final String value;

    public static Prefix commandToPrefix(String command) {
        Prefix result = null;
        for (Prefix prefix: Prefix.values()) {
            if (prefix.value.equals(command)) {
                result = prefix;
                break;
            }
        }
        return result;
    }

    Prefix(String value) {
        this.value = value;
    }
}
