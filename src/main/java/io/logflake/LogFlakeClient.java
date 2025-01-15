package io.logflake;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.logflake.enums.LogLevel;
import io.logflake.enums.Queue;
import io.logflake.utils.HttpClientWrapper;
import io.logflake.utils.LocalDateTimeAdapter;
import lombok.*;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.ByteArrayEntity;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.xerial.snappy.Snappy;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Getter
@Setter
public class LogFlakeClient {
    private final String PATH = "/api/ingestion/";
    @Builder.Default
    private String server = "https://app.logflake.io";
    @NonNull
    private final String appKey;
    private final String appId;
    private String hostname;
    private String correlation;
    @Builder.Default
    private Boolean enableCompression = true;
    @Builder.Default
    private Boolean verbose = true;


    private final HttpClient httpClient = new HttpClientWrapper().getHttpClient();
    private final ExecutorService executorService = Executors.newFixedThreadPool(5); // Async thread pool
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();


    public void sendLog(String message, LogLevel level, String correlation, String hostname, HashMap<String, String> params) {
        Runnable task = () -> {
            String uri = server + PATH + appKey + "/" + Queue.LOGS;
            HttpPost request = new HttpPost(uri);
            RequestLog log = RequestLog.builder()
                    .correlation(correlation != null ? correlation : this.correlation)
                    .hostname(hostname != null ? hostname : this.hostname)
                    .level(level.getIntValue())
                    .dateTime(LocalDateTime.now())
                    .content(message)
                    .params(params)
                    .build();


            if (enableCompression) {
                String json = gson.toJson(log);
                byte[] compressed = compress(json.getBytes());
                request.setEntity(new ByteArrayEntity(compressed, ContentType.APPLICATION_OCTET_STREAM));
                request.addHeader("Content-Type", "application/octet-stream");

            } else {
                String json = gson.toJson(log);
                request.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));
            }


            post(request);

        };

        executorService.submit(task);

    }

    public void sendLog(String message, LogLevel level, String correlation, String hostname) {
        Runnable task = () -> {
            String uri = server + PATH + appKey + "/" + Queue.LOGS;
            HttpPost request = new HttpPost(uri);
            RequestLog log = RequestLog.builder()
                    .correlation(correlation != null ? correlation : this.correlation)
                    .hostname(hostname != null ? hostname : this.hostname)
                    .level(level.getIntValue())
                    .dateTime(LocalDateTime.now())
                    .content(message)
                    .build();


            if (enableCompression) {
                String json = gson.toJson(log);
                byte[] compressed = compress(json.getBytes());
                request.setEntity(new ByteArrayEntity(compressed, ContentType.APPLICATION_OCTET_STREAM));
                request.addHeader("Content-Type", "application/octet-stream");

            } else {
                String json = gson.toJson(log);
                request.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));
            }


            post(request);

        };

        executorService.submit(task);

    }

    public void sendLog(String message, LogLevel level, String correlation, HashMap<String, String> params) {
        Runnable task = () -> {
            String uri = server + PATH + appKey + "/" + Queue.LOGS;
            HttpPost request = new HttpPost(uri);
            RequestLog log = RequestLog.builder()
                    .correlation(correlation != null ? correlation : this.correlation)
                    .hostname(hostname)
                    .level(level.getIntValue())
                    .dateTime(LocalDateTime.now())
                    .content(message)
                    .params(params)
                    .build();


            if (enableCompression) {
                String json = gson.toJson(log);
                byte[] compressed = compress(json.getBytes());
                request.setEntity(new ByteArrayEntity(compressed, ContentType.APPLICATION_OCTET_STREAM));
                request.addHeader("Content-Type", "application/octet-stream");

            } else {
                String json = gson.toJson(log);
                request.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));
            }


            post(request);

        };

        executorService.submit(task);

    }

    public void sendLog(String message, LogLevel level, String correlation) {
        Runnable task = () -> {
            String uri = server + PATH + appKey + "/" + Queue.LOGS;
            HttpPost request = new HttpPost(uri);
            RequestLog log = RequestLog.builder()
                    .correlation(correlation != null ? correlation : this.correlation)
                    .hostname(hostname)
                    .level(level.getIntValue())
                    .dateTime(LocalDateTime.now())
                    .content(message)
                    .build();


            if (enableCompression) {
                String json = gson.toJson(log);
                byte[] compressed = compress(json.getBytes());
                request.setEntity(new ByteArrayEntity(compressed, ContentType.APPLICATION_OCTET_STREAM));
                request.addHeader("Content-Type", "application/octet-stream");

            } else {
                String json = gson.toJson(log);
                request.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));
            }


            post(request);

        };

        executorService.submit(task);

    }

    public void sendLog(String message, LogLevel level, HashMap<String, String> params) {
        Runnable task = () -> {
            String uri = server + PATH + appKey + "/" + Queue.LOGS;
            HttpPost request = new HttpPost(uri);
            RequestLog log = RequestLog.builder()
                    .correlation(correlation)
                    .hostname(hostname)
                    .level(level.getIntValue())
                    .dateTime(LocalDateTime.now())
                    .content(message)
                    .params(params)
                    .build();


            if (enableCompression) {
                String json = gson.toJson(log);
                byte[] compressed = compress(json.getBytes());
                request.setEntity(new ByteArrayEntity(compressed, ContentType.APPLICATION_OCTET_STREAM));
                request.addHeader("Content-Type", "application/octet-stream");

            } else {
                String json = gson.toJson(log);
                request.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));
            }


            post(request);

        };

        executorService.submit(task);

    }

    public void sendLog(String message, LogLevel level) {
        Runnable task = () -> {
            String uri = server + PATH + appKey + "/" + Queue.LOGS;
            HttpPost request = new HttpPost(uri);
            RequestLog log = RequestLog.builder()
                    .correlation(correlation)
                    .hostname(hostname)
                    .level(level.getIntValue())
                    .dateTime(LocalDateTime.now())
                    .content(message)
                    .build();


            if (enableCompression) {
                String json = gson.toJson(log);
                byte[] compressed = compress(json.getBytes());
                request.setEntity(new ByteArrayEntity(compressed, ContentType.APPLICATION_OCTET_STREAM));
                request.addHeader("Content-Type", "application/octet-stream");

            } else {
                String json = gson.toJson(log);
                request.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));
            }


            post(request);

        };

        executorService.submit(task);

    }


    public void sendException(Throwable e) {


        Runnable task = () -> {
            String uri = server + PATH + appKey + "/" + Queue.LOGS;
            HttpPost request = new HttpPost(uri);

            String message = e.toString();
            StringBuilder stackTrace = new StringBuilder();
            for (StackTraceElement element : e.getStackTrace()) {
                stackTrace.append(element.toString()).append("\n");
            }
            message += "\n" + stackTrace;
            RequestLog log = RequestLog.builder()
                    .correlation(correlation)
                    .hostname(hostname)
                    .level(LogLevel.EXCEPTION.getIntValue())
                    .dateTime(LocalDateTime.now())
                    .content(message)
                    .build();


            if (enableCompression) {
                String json = gson.toJson(log);
                byte[] compressed = compress(json.getBytes());
                request.setEntity(new ByteArrayEntity(compressed, ContentType.APPLICATION_OCTET_STREAM));
                request.addHeader("Content-Type", "application/octet-stream");

            } else {
                String json = gson.toJson(log);
                request.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));
            }


            post(request);

        };

        executorService.submit(task);

    }
    public void sendException(Throwable e, HashMap<String, String> params) {


        Runnable task = () -> {
            String uri = server + PATH + appKey + "/" + Queue.LOGS;
            HttpPost request = new HttpPost(uri);

            String message = e.toString();
            StringBuilder stackTrace = new StringBuilder();
            for (StackTraceElement element : e.getStackTrace()) {
                stackTrace.append(element.toString()).append("\n");
            }
            message += "\n" + stackTrace;
            RequestLog log = RequestLog.builder()
                    .correlation(correlation)
                    .hostname(hostname)
                    .level(LogLevel.EXCEPTION.getIntValue())
                    .dateTime(LocalDateTime.now())
                    .content(message)
                    .params(params)
                    .build();


            if (enableCompression) {
                String json = gson.toJson(log);
                byte[] compressed = compress(json.getBytes());
                request.setEntity(new ByteArrayEntity(compressed, ContentType.APPLICATION_OCTET_STREAM));
                request.addHeader("Content-Type", "application/octet-stream");

            } else {
                String json = gson.toJson(log);
                request.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));
            }


            post(request);

        };

        executorService.submit(task);

    }

    public void sendException(Throwable e, HashMap<String, String> params, String correlation) {


        Runnable task = () -> {
            String uri = server + PATH + appKey + "/" + Queue.LOGS;
            HttpPost request = new HttpPost(uri);

            String message = e.toString();
            StringBuilder stackTrace = new StringBuilder();
            for (StackTraceElement element : e.getStackTrace()) {
                stackTrace.append(element.toString()).append("\n");
            }
            message += "\n" + stackTrace;
            RequestLog log = RequestLog.builder()
                    .correlation(correlation != null ? correlation : this.correlation)
                    .hostname(hostname)
                    .level(LogLevel.EXCEPTION.getIntValue())
                    .dateTime(LocalDateTime.now())
                    .content(message)
                    .params(params)
                    .build();


            if (enableCompression) {
                String json = gson.toJson(log);
                byte[] compressed = compress(json.getBytes());
                request.setEntity(new ByteArrayEntity(compressed, ContentType.APPLICATION_OCTET_STREAM));
                request.addHeader("Content-Type", "application/octet-stream");

            } else {
                String json = gson.toJson(log);
                request.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));
            }


            post(request);

        };

        executorService.submit(task);

    }

    public void sendException(Throwable e, HashMap<String, String> params, String correlation, String hostname) {


        Runnable task = () -> {
            String uri = server + PATH + appKey + "/" + Queue.LOGS;
            HttpPost request = new HttpPost(uri);

            String message = e.toString();
            StringBuilder stackTrace = new StringBuilder();
            for (StackTraceElement element : e.getStackTrace()) {
                stackTrace.append(element.toString()).append("\n");
            }
            message += "\n" + stackTrace;
            RequestLog log = RequestLog.builder()
                    .correlation(correlation != null ? correlation : this.correlation)
                    .hostname(hostname != null ? hostname : this.hostname)
                    .level(LogLevel.EXCEPTION.getIntValue())
                    .dateTime(LocalDateTime.now())
                    .content(message)
                    .params(params)
                    .build();


            if (enableCompression) {
                String json = gson.toJson(log);
                byte[] compressed = compress(json.getBytes());
                request.setEntity(new ByteArrayEntity(compressed, ContentType.APPLICATION_OCTET_STREAM));
                request.addHeader("Content-Type", "application/octet-stream");

            } else {
                String json = gson.toJson(log);
                request.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));
            }


            post(request);

        };

        executorService.submit(task);

    }

    private void post(HttpPost request) {
        try (CloseableHttpResponse ignored = httpClient.execute(request, httpResponse -> {
            int statusCode = httpResponse.getCode();
            if (statusCode != 200 && statusCode != 201 && statusCode != 202 && verbose) {
                System.err.println("Failed to send log. Status: " + statusCode + " Reason: " + httpResponse.getReasonPhrase());
            }
            return null;
        })) {

        } catch (Exception e) {
            System.out.println("SendLog: " + e.getMessage());
        }
    }

    private void sendPerformance(String label, long duration) {
        String uri = server + PATH + appKey + "/" + Queue.PERF;
        HttpPost request = new HttpPost(uri);
        RequestPerf perf = RequestPerf.builder()
                .duration(duration)
                .label(label)
                .build();
        Gson gson = new Gson();
        request.setEntity(new StringEntity(gson.toJson(perf)));
        post(request);
    }

    public void measurePerformance(String label) {
        long startTime = System.currentTimeMillis();
        Runnable task = () -> {
            long duration = System.currentTimeMillis() - startTime;
            try {
                sendPerformance(label, duration);
            } catch (Exception e) {
                System.out.println("MesurePerformance :" + e.getMessage());
            }
        };

        executorService.submit(task);
    }

    byte[] compress(byte[] data) {
        try {
            byte[] compressed = Base64.getEncoder().encode(data);
            return Snappy.compress(compressed);
        } catch (IOException e) {
            System.out.println("Compress: " + e.getMessage());
            return new byte[0];}
    }

    public void close() {
        executorService.shutdown();
    }
}
