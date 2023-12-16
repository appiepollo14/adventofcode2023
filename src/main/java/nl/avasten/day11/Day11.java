package nl.avasten.day11;

import jakarta.ws.rs.Path;
import nl.avasten.day10.Coordinate;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@Path("/day11")
public class Day11 {

    public static void mapInput() {

        try {
            Scanner scanner = new Scanner(new File("src/main/resources/puzzle11.txt"));

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
