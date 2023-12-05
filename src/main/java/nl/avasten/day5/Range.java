package nl.avasten.day5;

public class Range {

    private String type;
    private long destinationStart;
    private long destinationEnd;
    private long sourceStart;
    private long sourceEnd;

    public Range(String type, long destinationStart, long sourceStart, long range) {
        this.type = type;
        this.destinationStart = destinationStart;
        this.destinationEnd = destinationStart + range - 1;
        this.sourceStart = sourceStart;
        this.sourceEnd = sourceStart + range - 1;
    }

    public long getDestinationStart() {
        return destinationStart;
    }

    public long getDestinationEnd() {
        return destinationEnd;
    }

    public long getSourceStart() {
        return sourceStart;
    }

    public long getSourceEnd() {
        return sourceEnd;
    }

    @Override
    public String toString() {
        return "Range{" +
                "type='" + type + '\'' +
                ", destinationStart=" + destinationStart +
                ", destinationEnd=" + destinationEnd +
                ", sourceStart=" + sourceStart +
                ", sourceEnd=" + sourceEnd +
                '}';
    }
}
