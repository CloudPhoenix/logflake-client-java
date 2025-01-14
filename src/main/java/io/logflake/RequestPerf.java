package io.logflake;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
class RequestPerf {
    private long duration;
    private String label;
}
