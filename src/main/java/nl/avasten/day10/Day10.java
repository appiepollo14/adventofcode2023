package nl.avasten.day10;

import io.quarkus.runtime.Quarkus;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

@Path("/day10")
public class Day10 {


    static Coordinate startCoordinate;
    static Map<Integer, Map<Integer, Coordinate>> coordinates = new HashMap<>();

    static {
        mapInput();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String calculateSteps() {

        Coordinate first = determineFirstStep(startCoordinate);
        System.out.println("First: " + first);
        int steps = 1;
        Coordinate next = determineNextStep(startCoordinate, first);
        System.out.println("Next: " + next);
        while (true) {
            startCoordinate = first;
            first = next;
            next = determineNextStep(startCoordinate, first);
            System.out.println("Next: " + next);
            steps++;
            if (next == null) {
                break;
            }
        }

        return "Steps: " + (steps / 2);
    }

    public static Coordinate determineNextStep(Coordinate x0, Coordinate x1) {
        if (x1.chr() == '-') {
            if (x0.column() < x1.column()) {
                return getRight(x1);
            } else {
                return getLeft(x1);
            }
        } else if (x1.chr() == '7') {
            if (x0.row() == x1.row()) {
                return getBelow(x1);
            } else {
                return getLeft(x1);
            }
        } else if (x1.chr() == 'F') {
            if (x0.row() == x1.row()) {
                return getBelow(x1);
            } else {
                return getRight(x1);
            }
        } else if (x1.chr() == 'J') {
            if (x0.row() == x1.row()) {
                return getAbove(x1);
            } else {
                return getLeft(x1);
            }
        } else if (x1.chr() == 'L') {
            if (x0.row() == x1.row()) {
                return getAbove(x1);
            } else {
                return getRight(x1);
            }
        } else if (x1.chr() == '|') {
            if (x0.row() < x1.row()) {
                return getBelow(x1);
            } else {
                return getAbove(x1);
            }
        }
        return null;
    }

    public static Coordinate determineFirstStep(Coordinate c) {
        if (goLeft(c).isPresent()) {
            return goLeft(c).get();
        } else if (goRight(c).isPresent()) {
            return goRight(c).get();
        } else if (goUp(c).isPresent()) {
            return goUp(c).get();
        } else {
            return goDown(c).get();
        }
    }

    public static Coordinate getLeft(Coordinate c) {
        return coordinates.get(c.row()).get(c.column() - 1);
    }

    public static Coordinate getRight(Coordinate c) {
        return coordinates.get(c.row()).get(c.column() + 1);
    }

    public static Coordinate getAbove(Coordinate c) {
        return coordinates.get(c.row() - 1).get(c.column());
    }

    public static Coordinate getBelow(Coordinate c) {
        return coordinates.get(c.row() + 1).get(c.column());
    }

    public static Optional<Coordinate> goLeft(Coordinate c) {
        Coordinate n = coordinates.get(c.row()).get(c.column() - 1);
        char nc = n.chr();
        if (nc == '-' || nc == 'L' || nc == 'F') {
            return Optional.of(n);
        } else {
            return Optional.empty();
        }
    }

    public static Optional<Coordinate> goRight(Coordinate c) {
        Coordinate n = coordinates.get(c.row()).get(c.column() + 1);
        char nc = n.chr();
        if (nc == '-' || nc == 'J' || nc == '7') {
            return Optional.of(n);
        } else {
            return Optional.empty();
        }
    }

    public static Optional<Coordinate> goUp(Coordinate c) {
        Coordinate n = coordinates.get(c.row() - 1).get(c.column());
        char nc = n.chr();
        if (nc == '|' || nc == 'F' || nc == '7') {
            return Optional.of(n);
        } else {
            return Optional.empty();
        }
    }

    public static Optional<Coordinate> goDown(Coordinate c) {
        Coordinate n = coordinates.get(c.row() + 1).get(c.column());
        char nc = n.chr();
        if (nc == '|' || nc == 'L' || nc == 'J') {
            return Optional.of(n);
        } else {
            return Optional.empty();
        }
    }

    public static void mapInput() {

        try {
            Scanner scanner = new Scanner(new File("src/main/resources/puzzle10.txt"));

            int lineNum = 0;
            while (scanner.hasNextLine()) {
                Map<Integer, Coordinate> coordinateLine = new HashMap<>();
                String currentLine = scanner.nextLine();
                for (int i = 0; i < currentLine.length(); i++) {
                    coordinateLine.put(i, new Coordinate(lineNum, i, currentLine.charAt(i)));
                    if (currentLine.charAt(i) == 'S') {
                        startCoordinate = new Coordinate(lineNum, i, 'S');
                    }
                }
                coordinates.put(lineNum, coordinateLine);
                lineNum++;
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
