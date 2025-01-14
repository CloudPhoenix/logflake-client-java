package io.logflake;

import org.apache.hc.client5.http.classic.HttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.xerial.snappy.Snappy;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class LogFlakeTest {

    private LogFlake logFlake;
    private HttpClient mockHttpClient;
    private ExecutorService mockExecutorService;

    @BeforeEach
    void setUp() {
        mockHttpClient = mock(HttpClient.class);
        mockExecutorService = mock(ExecutorService.class);
        logFlake = LogFlake.builder()
                .appKey("appKey")
                .appId("appId")
                .hostname("hostname")
                .correlation("correlation")
                .enableCompression(true)
                .verbose(true)
                .build();
    }

    @Test
    void compressReturnsCompressedData() throws IOException {
        LogFlake logFlake = LogFlake.builder()
                .appKey("appKey")
                .appId("appId")
                .hostname("hostname")
                .correlation("correlation")
                .enableCompression(true)
                .verbose(true)
                .build();        byte[] data = "test data".getBytes();
        byte[] compressedData = logFlake.compress(data);

        assertNotNull(compressedData);
        assertTrue(compressedData.length > 0);
    }

    @Test
    void compressHandlesEmpty() {
        LogFlake logFlake = LogFlake.builder()
                .appKey("appKey")
                .appId("appId")
                .hostname("hostname")
                .correlation("correlation")
                .enableCompression(true)
                .verbose(true)
                .build();
        byte[] data = new byte[0]; // Empty data to simulate IOException
        byte[] compressedData = logFlake.compress(data);

        assertNotNull(compressedData);
        assertEquals(1, compressedData.length);
    }

    @Test
    void compressHandlesIOException() {
        LogFlake logFlake = LogFlake.builder()
                .appKey("appKey")
                .appId("appId")
                .hostname("hostname")
                .correlation("correlation")
                .enableCompression(true)
                .verbose(true)
                .build();

        // Mock Snappy.compress to throw IOException
        try (MockedStatic<Snappy> mockedSnappy = mockStatic(Snappy.class)) {
            mockedSnappy.when(() -> Snappy.compress(any(byte[].class))).thenThrow(IOException.class);

            byte[] data = "test data".getBytes();
            byte[] compressedData = logFlake.compress(data);

            assertNotNull(compressedData);
            assertEquals(0, compressedData.length);
        }
    }

}