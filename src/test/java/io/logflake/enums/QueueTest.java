package io.logflake.enums;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class QueueTest {
    /**
     * Test {@link Queue#toString()}.
     * <p>
     * Method under test: {@link Queue#toString()}
     */
    @Test
    @DisplayName("Test toString()")
    void testToString() {
        // Arrange, Act and Assert
        assertEquals("logs", Queue.valueOf("LOGS").toString());
    }
}
