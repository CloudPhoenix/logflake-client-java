package io.logflake.utils;

import org.apache.hc.client5.http.HttpRequestRetryStrategy;
import org.apache.hc.client5.http.HttpResponseException;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.core5.http.HttpRequest;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.protocol.HttpContext;
import org.apache.hc.core5.util.TimeValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HttpClientWrapperTest {

    private HttpClientWrapper httpClientWrapper;

    private HttpContext mockContext;

    private HttpResponse mockResponse;

    @BeforeEach
    void setUp() {
        httpClientWrapper = new HttpClientWrapper();
        mockContext = mock(HttpContext.class);
        mockResponse = mock(HttpResponse.class);
    }

    /**
     * Test {@link HttpClientWrapper#getHttpClient()}.
     * <p>
     * Method under test: {@link HttpClientWrapper#getHttpClient()}
     */



    @Test
    void retryRequestReturnsFalseForIOException() throws IOException {
        HttpRequestRetryStrategy retryStrategy = httpClientWrapper.getRetryStrategy();
        HttpRequest request = new HttpGet("http://example.com");

        boolean result = retryStrategy.retryRequest(request, new IOException(), 1, mockContext);

        assertFalse(result);
    }

    @Test
    void retryRequestReturnsFalseForExecCountGreaterThanFive() throws IOException {
        HttpRequestRetryStrategy retryStrategy = httpClientWrapper.getRetryStrategy();
        HttpRequest request = new HttpGet("http://example.com");

        boolean result = retryStrategy.retryRequest(mockResponse, 6, mockContext);

        assertFalse(result);
    }

    @Test
    void retryRequestReturnsFalseForCertainStatusCodes() throws IOException {
        HttpRequestRetryStrategy retryStrategy = httpClientWrapper.getRetryStrategy();
        HttpRequest request = new HttpGet("http://example.com");

        when(mockResponse.getCode()).thenReturn(200);
        boolean result = retryStrategy.retryRequest(mockResponse, 1, mockContext);
        assertFalse(result);

        when(mockResponse.getCode()).thenReturn(201);
        result = retryStrategy.retryRequest(mockResponse, 1, mockContext);
        assertFalse(result);

        when(mockResponse.getCode()).thenReturn(202);
        result = retryStrategy.retryRequest(mockResponse, 1, mockContext);
        assertFalse(result);

        when(mockResponse.getCode()).thenReturn(203);
        result = retryStrategy.retryRequest(mockResponse, 1, mockContext);
        assertFalse(result);
    }

    @Test
    void retryRequestReturnsTrueForOtherStatusCodes() throws IOException {
        HttpRequestRetryStrategy retryStrategy = httpClientWrapper.getRetryStrategy();
        HttpRequest request = new HttpGet("http://example.com");

        when(mockResponse.getCode()).thenReturn(500);
        boolean result = retryStrategy.retryRequest(mockResponse, 1, mockContext);
        assertTrue(result);
    }

    @Test
    void getRetryIntervalReturnsCorrectTimeValue() throws IOException {
        HttpRequestRetryStrategy retryStrategy = httpClientWrapper.getRetryStrategy();
        HttpRequest request = new HttpGet("http://example.com");

        TimeValue interval = retryStrategy.getRetryInterval(mockResponse, 2, mockContext);

        assertEquals(TimeValue.ofSeconds(20), interval);
    }

    @Test
    void getRetryIntervalReturnsDefaultTimeValue() throws IOException {
        HttpRequestRetryStrategy retryStrategy = httpClientWrapper.getRetryStrategy();
        HttpRequest request = new HttpGet("http://example.com");

        TimeValue interval = retryStrategy.getRetryInterval(request, new IOException(), 2, mockContext);

        assertEquals(TimeValue.ofMilliseconds(0), interval);
    }

    @Test
    void getHttpClientReturnsNonNullHttpClient() {
        HttpClient httpClient = httpClientWrapper.getHttpClient();
        assertNotNull(httpClient);
    }

    @Test
    void getHttpClientReturnsSameInstance() {
        HttpClient firstInstance = httpClientWrapper.getHttpClient();
        HttpClient secondInstance = httpClientWrapper.getHttpClient();
        assertSame(firstInstance, secondInstance);
    }
}