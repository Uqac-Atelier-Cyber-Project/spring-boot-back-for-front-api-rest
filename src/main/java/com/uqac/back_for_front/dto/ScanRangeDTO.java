package com.uqac.back_for_front.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ScanRangeDTO {
    // Getters et Setters
    private int start;
    private int end;

    public ScanRangeDTO(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        return "ScanRangeDTO{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }

    public String toJSon() {
        return "{" +
                "\"start\":" + start +
                ", \"end\":" + end +
                '}';
    }
}
