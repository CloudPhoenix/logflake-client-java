package io.logflake;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Builder
@Setter
@Getter
class RequestLog {
    private Object content;
    private String correlation;
    private String hostname;
    private String id;
    private Integer level;
    private Map<String, String> params;
    private LocalDateTime dateTime;
}
