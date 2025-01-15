package io.logflake;


import io.logflake.enums.LogLevel;
import lombok.NonNull;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.impl.Log4jLogEvent;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.apache.logging.log4j.message.MessageFactory;
import org.apache.logging.log4j.message.SimpleMessage;

import java.util.HashMap;


public class Log4jWrapper {
    private final Logger logger;
    private final LogFlakeClient logFlake;

    private Log4jWrapper(Logger logger,@NonNull LogFlakeClient logFlake) {
        this.logger = logger;
        this.logFlake = logFlake;
    }


    private Log4jWrapper(Class<?> clazz, @NonNull LogFlakeClient logFlake) {
        this.logger = LogManager.getLogger(clazz);
        this.logFlake = logFlake;
    }

    private Log4jWrapper(Class<?> clazz, MessageFactory messageFactory,@NonNull LogFlakeClient logFlake) {
        this.logger = LogManager.getLogger(clazz, messageFactory);
        this.logFlake = logFlake;
    }

    private Log4jWrapper(MessageFactory messageFactory, @NonNull LogFlakeClient logFlake) {
        this.logger = LogManager.getLogger(messageFactory);
        this.logFlake = logFlake;
    }

    private Log4jWrapper(LogFlakeClient logFlake) {
        this.logger = LogManager.getLogger();
        this.logFlake = logFlake;
    }

    private Log4jWrapper(String name, @NonNull LogFlakeClient logFlake) {
        this.logger = LogManager.getLogger(name);
        this.logFlake = logFlake;
    }

    private Log4jWrapper(String name, MessageFactory messageFactory, @NonNull LogFlakeClient logFlake) {
        this.logger = LogManager.getLogger(name, messageFactory);
        this.logFlake = logFlake;
    }


    private Log4jWrapper(Object value, @NonNull LogFlakeClient logFlake) {
        this.logger = LogManager.getLogger(value);
        this.logFlake = logFlake;
    }

    private Log4jWrapper(Object value, MessageFactory messageFactory, @NonNull LogFlakeClient logFlake) {
        this.logger = LogManager.getLogger(value, messageFactory);
        this.logFlake = logFlake;
    }


    public static Log4jWrapper getLogger(Class<?> clazz,@NonNull LogFlakeClient logFlake) {
        return new Log4jWrapper(clazz, logFlake);
    }


    public static Log4jWrapper getLogger(Class<?> clazz, MessageFactory messageFactory, @NonNull LogFlakeClient logFlake) {
        return new Log4jWrapper(clazz, messageFactory, logFlake);
    }

    public static Log4jWrapper getLogger(MessageFactory messageFactory, @NonNull LogFlakeClient logFlake) {
        return new Log4jWrapper(messageFactory, logFlake);
    }

    public static Log4jWrapper getLogger(LogFlakeClient logFlake) {
        return new Log4jWrapper(logFlake);
    }

    public static Log4jWrapper getLogger(String name, LogFlakeClient logFlake) {
        return new Log4jWrapper(name, logFlake);
    }

    public static Log4jWrapper getLogger(String name, MessageFactory messageFactory, @NonNull LogFlakeClient logFlake) {
        return new Log4jWrapper(name, messageFactory, logFlake);
    }

    public static Log4jWrapper getLogger(Object value, @NonNull LogFlakeClient logFlake) {
        return new Log4jWrapper(value, logFlake);
    }

    public static Log4jWrapper getLogger(Object value, MessageFactory messageFactory, @NonNull LogFlakeClient logFlake) {
        return new Log4jWrapper(value, messageFactory, logFlake);
    }


    public void info(String message,Object... params ) {
        logger.info(message, params);
        PatternLayout layout = getPatternLayout();
        LogEvent event = Log4jLogEvent.newBuilder()
                .setLoggerName(logger.getName())
                .setLevel(org.apache.logging.log4j.Level.INFO)
                .setMessage(new SimpleMessage(message))
                .build();
        logFlake.sendLog(layout.toSerializable(event), LogLevel.INFO);
    }


    public void debug(String message ,Object... params) {
        logger.debug(message, params);
        PatternLayout layout = getPatternLayout();
        LogEvent event = Log4jLogEvent.newBuilder()
                .setLoggerName(logger.getName())
                .setLevel(Level.DEBUG)
                .setMessage(new SimpleMessage(message))
                .build();
        logFlake.sendLog(layout.toSerializable(event), LogLevel.DEBUG);
    }

    public void warn(String message, Object... params) {
        logger.warn(message, params);
        PatternLayout layout = getPatternLayout();
        LogEvent event = Log4jLogEvent.newBuilder()
                .setLoggerName(logger.getName())
                .setLevel(Level.WARN)
                .setMessage(new SimpleMessage(message))
                .build();
        logFlake.sendLog(layout.toSerializable(event), LogLevel.WARNING);
    }

    public void error(String message, Throwable throwable, HashMap<String,String> params) {
        logger.error(message, throwable);
        logFlake.sendException( throwable, params);
    }

    public void error(String message, Object... params) {
        logger.error(message, params);
        PatternLayout layout = getPatternLayout();
        LogEvent event = Log4jLogEvent.newBuilder()
                .setLoggerName(logger.getName())
                .setLevel(Level.ERROR)
                .setMessage(new SimpleMessage(message))
                .build();
        logFlake.sendLog(layout.toSerializable(event), LogLevel.ERROR);
    }

    public void fatal(String message, Object... params) {
        logger.fatal(message, params);
        PatternLayout layout = getPatternLayout();
        LogEvent event = Log4jLogEvent.newBuilder()
                .setLoggerName(logger.getName())
                .setLevel(Level.FATAL)
                .setMessage(new SimpleMessage(message))
                .build();
        logFlake.sendLog(layout.toSerializable(event), LogLevel.FATAL);
    }

    public void trace(String message, Object... params) {
        logger.trace(message, params);
    }

    public void destroy() {
        logFlake.close();
    }

    private static PatternLayout getPatternLayout() {
        LoggerContext context = (LoggerContext) LogManager.getContext(false);
        Configuration config = context.getConfiguration();
        return (PatternLayout) config.getAppenders().values().stream()
                .findFirst()
                .map(Appender::getLayout)
                .filter(layout -> layout instanceof PatternLayout).orElse(null);
    }
}
