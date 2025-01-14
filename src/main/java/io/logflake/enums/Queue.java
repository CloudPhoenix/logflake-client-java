package io.logflake.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Queue {
    LOGS("logs"),
    PERF("perf");

    private final String s;

    @Override
    public String toString() {
        return s;
    }



}
