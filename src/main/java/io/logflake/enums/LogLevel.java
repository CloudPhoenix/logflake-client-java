package io.logflake.enums;

public enum LogLevel {
    DEBUG(0),
    INFO(1),
    WARN(2),
    ERROR(3),
    FATAL(4),
    EXCEPTION(5);

    private final int level;

    LogLevel(int level) {
        this.level = level;
    }

    public int getIntValue() {
        return level;
    }
}
