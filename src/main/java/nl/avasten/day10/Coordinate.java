package nl.avasten.day10;

public record Coordinate(int row, int column, char chr) {
    @Override
    public String toString() {
        return "Coordinate{" +
                "row=" + row +
                ", column=" + column +
                ", chr=" + chr +
                '}';
    }
}
