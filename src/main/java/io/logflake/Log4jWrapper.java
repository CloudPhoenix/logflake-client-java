package io.logflake;


import io.logflake.enums.LogLevel;
import lombok.Getter;
import lombok.NonNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;

@Getter
public class Log4jWrapper {

    private final Logger logger;
    private final LogFlake logFlake;

    private Log4jWrapper(Class<?> clazz, LogFlake logFlake) {
        this.logger = LogManager.getLogger(clazz);
        this.logFlake = logFlake;
    }

    public static Log4jWrapper getLogger(Class<?> clazz, LogFlake logFlake) {
        return new Log4jWrapper(clazz, logFlake);
    }


    private Log4jWrapper(Class<?> clazz, String server, String appKey, String appId, String hostname, String correlation, Boolean enableCompression, Boolean verbose) {
        this.logger = LogManager.getLogger(clazz);
        this.logFlake = LogFlake.builder()
                .server(server)
                .appKey(appKey)
                .appId(appId)
                .hostname(hostname)
                .correlation(correlation)
                .enableCompression(enableCompression)
                .verbose(verbose)
                .build();
    }

    public static Log4jWrapper getLogger(Class<?> clazz, String server,
                                         @NonNull
                                         String appKey, Boolean enableCompression, Boolean verbose,String appId, String hostname, String correlation) {
        return new Log4jWrapper(clazz, server, appKey, appId, hostname, correlation, enableCompression, verbose);
    }

    public void info(String message,Object... params ) {
        logger.info(message, params);
        logFlake.sendLog(message, LogLevel.INFO);
    }

    public void debug(String message ,Object... params) {
        logger.debug(message, params);
        logFlake.sendLog( message, LogLevel.DEBUG);
    }

    public void warn(String message, Object... params) {
        logger.warn(message, params);
        logFlake.sendLog( message, LogLevel.WARN);
    }

    public void error(String message, Throwable throwable, HashMap<String,String> params) {
        logger.error(message, throwable);
        logFlake.sendException( throwable, params);
    }

    public void error(String message, Object... params) {
        logger.error(message, params);
        logFlake.sendLog( message, LogLevel.ERROR);
    }

    public void trace(String message, Object... params) {
        logger.trace(message, params);
    }

    public void destroy() {
        logFlake.close();
    }

}
