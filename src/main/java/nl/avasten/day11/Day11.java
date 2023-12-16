package nl.avasten.day11;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import nl.avasten.day10.Coordinate;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

@Path("/day11")
public class Day11 {

    static Map<Coordinate, Character> coordinates = new HashMap<>();

    static {
        mapInput();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String calculateSteps() {

        int steps = 0;
//        System.out.println(coordinates);
        return "Steps: " + (steps / 2);
    }

    public static void duplicateRows(List<Integer> rowNums) {

    }

    public static List<Coordinate> getColumn(int colNum) {
        List<Coordinate> l = new ArrayList<>();
        for (Coordinate c : coordinates.keySet()) {
            if (c.column() == colNum) {
                l.add(c);
            }
        }
        return l;
    }

    public static boolean columnContainsHashTag(List<Coordinate> coordinates) {
        boolean anyHashtag = false;
        for (Coordinate c : coordinates) {
            if (c.chr() == '#') {
                anyHashtag = true;
            }
        }
        return anyHashtag;
    }

    public static void mapInput() {

        try {
            Scanner scanner = new Scanner(new File("src/main/resources/puzzle11.txt"));
            List<Integer> noHashTagRowNums = new ArrayList<>();
            List<Integer> noHashTagColumnNums = new ArrayList<>();
            int lineNum = 0;
            int columns = 0;
            int galaxy = 0;
            while (scanner.hasNextLine()) {
                String currentLine = scanner.nextLine();
                columns = currentLine.length();
                boolean anyHashtag = false;
                for (int i = 0; i < currentLine.length(); i++) {
                    if (currentLine.charAt(i) == '#') {
                        anyHashtag = true;
                    }
                }
                if (!anyHashtag) {
                    noHashTagRowNums.add(lineNum);
                }
                for (int i = 0; i < currentLine.length(); i++) {
                    if (currentLine.charAt(i) == '#') {
//                        coordinates.put(new Coordinate(lineNum, i, currentLine.charAt(i)), (char) galaxy);
                        coordinates.put(new Coordinate(lineNum, i, currentLine.charAt(i)), currentLine.charAt(i));
                        galaxy++;
                    } else {
                        coordinates.put(new Coordinate(lineNum, i, currentLine.charAt(i)), currentLine.charAt(i));
                    }
                }

                lineNum++;
            }

            scanner.close();

            for (int i = 0; i < columns; i++) {
                if (!columnContainsHashTag(getColumn(i))) {
                    noHashTagColumnNums.add(i);
                    System.out.println("No hashtags in column: " + i);
                }
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }
}
