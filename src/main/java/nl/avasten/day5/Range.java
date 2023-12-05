package nl.avasten.day4;

import java.util.ArrayList;
import java.util.List;

public class Range {

    private String type;
    private Long start;
    private Long end;

    public Range(String type, Long start, Long end) {
        this.type = type;
        this.start = start;
        this.end = end;
    }

    public String getType() {
        return type;
    }

    public Long getStart() {
        return start;
    }


    public Long getEnd() {
        return end;
    }

}
