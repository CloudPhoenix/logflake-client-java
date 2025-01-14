package io.logflake.enums;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LogLevelTest {
    /**
     * Test {@link LogLevel#getIntValue()}.
     * <p>
     * Method under test: {@link LogLevel#getIntValue()}
     */
    @Test
    @DisplayName("Test getIntValue()")
    void testGetIntValue() {
        // Arrange, Act and Assert
        assertEquals(0, LogLevel.valueOf("DEBUG").getIntValue());
    }
}
