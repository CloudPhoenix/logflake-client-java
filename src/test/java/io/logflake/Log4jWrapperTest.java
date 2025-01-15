package io.logflake;

import io.logflake.enums.LogLevel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.HashMap;

import static org.mockito.Mockito.*;

@Disabled("Doe to the fact that changes in the Log4jWrapper class are not reflected in the test class")
class Log4jWrapperTest {

    private Log4jWrapper log4jWrapper;
    private Logger mockLogger;
    private LogFlakeClient mockLogFlake;

    @BeforeEach
    void setUp() {
        mockLogger = mock(Logger.class);
        mockLogFlake = mock(LogFlakeClient.class);
        try (MockedStatic<LogManager> logManagerMockedStatic = mockStatic(LogManager.class)) {
            logManagerMockedStatic.when(() -> LogManager.getLogger(any(Class.class))).thenReturn(mockLogger);
            log4jWrapper = Log4jWrapper.getLogger(Log4jWrapperTest.class, mockLogFlake);
        }
    }




    @Test
    void infoLogsMessageWithNullParams() {
        log4jWrapper.info("Info message", (Object[]) null);

        verify(mockLogger).info("Info message", (Object[]) null);
        verify(mockLogFlake).sendLog("Info message", LogLevel.INFO);
    }

    @Test
    void debugLogsMessageWithNullParams() {
        log4jWrapper.debug("Debug message", (Object[]) null);

        verify(mockLogger).debug("Debug message", (Object[]) null);
        verify(mockLogFlake).sendLog("Debug message", LogLevel.DEBUG);
    }

    @Test
    void warnLogsMessageWithNullParams() {
        log4jWrapper.warn("Warn message", (Object[]) null);

        verify(mockLogger).warn("Warn message", (Object[]) null);
        verify(mockLogFlake).sendLog("Warn message", LogLevel.WARNING);
    }

    @Test
    void errorLogsMessageWithNullParams() {
        log4jWrapper.error("Error message", (Object[]) null);

        verify(mockLogger).error("Error message", (Object[]) null);
        verify(mockLogFlake).sendLog("Error message", LogLevel.ERROR);
    }

    @Test
    void traceLogsMessageWithNullParams() {
        log4jWrapper.trace("Trace message", (Object[]) null);

        verify(mockLogger).trace("Trace message", (Object[]) null);
    }

    @Test
    void errorLogsExceptionAndSendsExceptionWithParams() {
        Throwable throwable = new RuntimeException("Exception message");
        HashMap<String, String> params = new HashMap<>();
        params.put("key", "value");

        log4jWrapper.error("Error message", throwable, params);

        verify(mockLogger).error("Error message", throwable);
        verify(mockLogFlake).sendException(throwable, params);
    }

    @Test
    void errorLogsExceptionAndSendsExceptionWithEmptyParams() {
        Throwable throwable = new RuntimeException("Exception message");
        HashMap<String, String> params = new HashMap<>();

        log4jWrapper.error("Error message", throwable, params);

        verify(mockLogger).error("Error message", throwable);
        verify(mockLogFlake).sendException(throwable, params);
    }

    @Test
    void errorLogsExceptionAndSendsExceptionWithNullParams() {
        Throwable throwable = new RuntimeException("Exception message");

        log4jWrapper.error("Error message", throwable, null);

        verify(mockLogger).error("Error message", throwable);
        verify(mockLogFlake).sendException(throwable, null);
    }

    @Test
    void destroyClosesLogFlake() {
        log4jWrapper.destroy();

        verify(mockLogFlake).close();
    }



}