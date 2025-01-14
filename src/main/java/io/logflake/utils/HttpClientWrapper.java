package io.logflake.utils;

import lombok.Getter;
import lombok.Setter;
import org.apache.hc.client5.http.HttpRequestRetryStrategy;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.*;
import org.apache.hc.core5.http.protocol.HttpContext;
import org.apache.hc.core5.util.TimeValue;

import java.io.IOException;

@Getter
@Setter

public class HttpClientWrapper {

    private HttpClient httpClient;
    private HttpRequestRetryStrategy retryStrategy;

    public HttpClientWrapper() {
        this.retryStrategy = new HttpRequestRetryStrategy() {
            @Override
            public boolean retryRequest(HttpRequest request, IOException exception, int execCount, HttpContext context) {
                return false;
            }

            @Override
            public boolean retryRequest(HttpResponse response, int execCount, HttpContext context) {
                if (execCount > 5) {
                    return false;
                }
                return !(response.getCode() == 203 || response.getCode() == 200 || response.getCode() == 201 || response.getCode() == 202);
            }

            @Override
            public TimeValue getRetryInterval(HttpRequest request, IOException exception, int execCount, HttpContext context) {
                return HttpRequestRetryStrategy.super.getRetryInterval(request, exception, execCount, context);
            }

            @Override
            public TimeValue getRetryInterval(HttpResponse response, int execCount, HttpContext context) {
                return TimeValue.ofSeconds(10L * execCount);
            }
        };

        this.httpClient = HttpClientBuilder.create()
                .setRetryStrategy(retryStrategy)
                .build();
    }

}