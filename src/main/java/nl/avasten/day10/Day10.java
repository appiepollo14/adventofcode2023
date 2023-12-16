package nl.avasten.day10;

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

    static int startPuntRij;
    static int startPuntKolom;
    static int steps = 0;
    static Coordinate startCoordinate;
    static Map<Integer, Map<Integer, Character>> mappedInput = new HashMap<>();
    static Map<Integer, Map<Integer, Coordinate>> coordinates = new HashMap<>();

    static {
        mapInput();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String calculateSteps() {

        System.out.println("Startpunt rij: " + startPuntRij + ", kolom: " + startPuntKolom);
        System.out.println("Mapped input: " + mappedInput);

        determineNextStep(startCoordinate);

        return "Sequences: " + steps;
    }

    public static void determineNextStep(Coordinate c) {
        steps++;
        if (goLeft(c).isPresent()) {
            determineNextStep(goLeft(c).get());
        } else if (goRight(c).isPresent()) {
            determineNextStep(goRight(c).get());
        } else if (goUp(c).isPresent()) {
            determineNextStep(goUp(c).get());
        } else if (goDown(c).isPresent()){
            determineNextStep(goDown(c).get());
        }
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
                Map<Integer, Character> mappedLine = new HashMap<>();
                Map<Integer, Coordinate> coordinateLine = new HashMap<>();
                String currentLine = scanner.nextLine();
                for (int i = 0; i < currentLine.length(); i++) {
                    mappedLine.put(i, currentLine.charAt(i));
                    coordinateLine.put(i, new Coordinate(lineNum, i, currentLine.charAt(i)));
                    if (currentLine.charAt(i) == 'S') {
                        startPuntRij = lineNum;
                        startPuntKolom = i;
                        startCoordinate = new Coordinate(lineNum, i, 'S');
                    }
                }
                mappedInput.put(lineNum, mappedLine);
                coordinates.put(lineNum, coordinateLine);
                lineNum++;
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
