package nl.avasten;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

@Path("/day3")
public class Day3puzzle2 {

    static final int lineSize = 140;
    static Map<Integer, List<Integer>> gearSymbolsPerLine = new HashMap<>();
    static Map<Integer, String> mappedInput = inputToMap();
    static Map<Integer, Map<List<Integer>, Integer>> numbersPerLine = new HashMap<>();

    @GET
    @Path("/puzzle2")
    public String calculateGearRatio() {
        int sum = 0;

        //Map gear symbol Positions
        for (int i = 0; i < mappedInput.size(); i++) {
            gearSymbolsPerLine.put(i, getGearSymbolPositions(mappedInput.get(i)));
        }

        // Get numbers per line,
        for (int i = 0; i < mappedInput.size(); i++) {
            numbersPerLine.put(i, getNumberPositionMap(mappedInput.get(i)));
        }

        // check adjecening fields for each number, if found, add to sum
        for (int i = 0; i < gearSymbolsPerLine.size(); i++) {
            List<Integer> gearPositions = gearSymbolsPerLine.get(i);
            // First row
            if (i == 0) {
                // Loop through each number
                for (int position : gearPositions) {
                    Set<Integer> matches = checkSameLine(i, position);
                    matches.addAll(checkLineBelow(i, position));
                    if (matches.size() == 2) {
                        int value1 = matches.iterator().next();
                        int value2 = matches.stream().skip(1).findFirst().orElseThrow();

                        // Multiply the values
                        sum += value1 * value2;
                    }
                }

                // Last row
            } else if (i == gearSymbolsPerLine.size() - 1) {
                // Loop through each number
                for (int position : gearPositions) {
                    Set<Integer> matches = checkSameLine(i, position);
                    matches.addAll(checkLineAbove(i, position));
                    if (matches.size() == 2) {
                        int value1 = matches.iterator().next();
                        int value2 = matches.stream().skip(1).findFirst().orElseThrow();

                        // Multiply the values
                        sum += value1 * value2;
                    }
                }
                // All other rows
            } else {
                // Loop through each number
                for (int position : gearPositions) {
                    Set<Integer> matches = checkSameLine(i, position);
                    matches.addAll(checkLineBelow(i, position));
                    matches.addAll(checkLineAbove(i, position));
                    if (matches.size() == 2) {
                        int value1 = matches.iterator().next();
                        int value2 = matches.stream().skip(1).findFirst().orElseThrow();

                        // Multiply the values
                        sum += value1 * value2;
                    }
//                    System.out.println("Matches: " + matches);
                }
            }
        }

        return "Sum: " + sum;

    }

    private static Map<Integer, String> inputToMap() {

        Map<Integer, String> mappedInput = new HashMap<>();
        int lineNumber = 0;

        try {
            Scanner scanner = new Scanner(new File("src/main/resources/puzzle3.txt"));

            while (scanner.hasNextLine()) {
                String currentLine = scanner.nextLine();
                currentLine = currentLine.replaceAll("\\.", " ");
                mappedInput.put(lineNumber, currentLine);
                lineNumber++;
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return mappedInput;
    }

    private static List<Integer> getGearSymbolPositions(String currentLine) {
        List<Integer> symbolPositions = new ArrayList<>();

        for (int i = 0; i < currentLine.length(); i++) {
            if (isGearSymbol(currentLine.charAt(i))) {
                symbolPositions.add(i);
            }

        }
        return symbolPositions;

    }

    public static int checkLeft(int lineNumber, int position) {

        Map<List<Integer>, Integer> numberPositionsPerLine = numbersPerLine.get(lineNumber);

        int left = position - 1;
        if (left > 0) {

            for (List<Integer> k : numberPositionsPerLine.keySet()) {
                if (k.contains(left)) {
                    int num = numberPositionsPerLine.get(k);
                    return num;
                }
            }
        }

        return 0;
    }

    public static int checkRight(int lineNumber, int position) {
        Map<List<Integer>, Integer> numberPositionsPerLine = numbersPerLine.get(lineNumber);
        int right = position + 1;
        if (right < lineSize) {

            for (List<Integer> k : numberPositionsPerLine.keySet()) {
                if (k.contains(right)) {
                    int num = numberPositionsPerLine.get(k);
                    System.out.println("Line: " + lineNumber + " same line hit: " + num);
                    return num;
                }
            }
        }

        return 0;
    }

    private static Set<Integer> checkSameLine(int lineNumber, int position) {
        Set<Integer> matches = new HashSet<>();

        if (checkLeft(lineNumber, position) != 0) {
            matches.add(checkLeft(lineNumber, position));
        }

        if (checkRight(lineNumber, position) != 0) {
            matches.add(checkRight(lineNumber, position));
        }

        return matches;
    }

    private static Set<Integer> checkLineBelow(int lineNumber, int position) {

        Set<Integer> matches = new HashSet<>();

        int lineNumberBelow = lineNumber + 1;
        Map<List<Integer>, Integer> numberPositionsPerLine = numbersPerLine.get(lineNumberBelow);

        // Get leftUnder from number:
        int left = position - 1;
        if (left > 0) {

            for (List<Integer> k : numberPositionsPerLine.keySet()) {
                if (k.contains(left)) {
                    matches.add(numberPositionsPerLine.get(k));
                }
            }
        }

        // Get rightUnder
        int right = position + 1;
        if (right < lineSize) {

            for (List<Integer> k : numberPositionsPerLine.keySet()) {
                if (k.contains(right)) {
                    matches.add(numberPositionsPerLine.get(k));
                }
            }
        }

        // Get under
        for (List<Integer> k : numberPositionsPerLine.keySet()) {
            if (k.contains(position)) {
                matches.add(numberPositionsPerLine.get(k));
            }
        }

        return matches;
    }

    private static Set<Integer> checkLineAbove(int lineNumber, int position) {

        Set<Integer> matches = new HashSet<>();

        int lineNumberAbove = lineNumber - 1;
        Map<List<Integer>, Integer> numberPositionsPerLine = numbersPerLine.get(lineNumberAbove);

        // Get leftAbove from number:
        int left = position - 1;
        if (left > 0) {

            for (List<Integer> k : numberPositionsPerLine.keySet()) {
                if (k.contains(left)) {
                    matches.add(numberPositionsPerLine.get(k));
                }
            }
        }

        // Get rightAbove
        int right = position + 1;
        if (right < lineSize) {

            for (List<Integer> k : numberPositionsPerLine.keySet()) {
                if (k.contains(right)) {
                    matches.add(numberPositionsPerLine.get(k));
                }
            }
        }

        //Get above
        for (List<Integer> k : numberPositionsPerLine.keySet()) {
            if (k.contains(position)) {
                matches.add(numberPositionsPerLine.get(k));
            }
        }

        return matches;
    }

    private static boolean isGearSymbol(char ch) {
        return ch == '*';
    }

    private static boolean isNumber(char ch) {
        return Character.isDigit(ch);
    }

    private static Map<List<Integer>, Integer> getNumberPositionMap(String currentLine) {
        Map<List<Integer>, Integer> numberPositionMap = new HashMap<>();

        StringBuilder number = new StringBuilder();
        List<Integer> positions = new ArrayList<>();
        for (int i = 0; i < currentLine.length(); i++) {

            if (isNumber(currentLine.charAt(i))) {
                number.append(currentLine.charAt(i));
                positions.add(i);
            } else {
                if (!number.isEmpty()) {
                    numberPositionMap.put(positions, Integer.parseInt(number.toString()));
                    number = new StringBuilder();
                    positions = new ArrayList<>();
                }
            }

        }
        if (!number.isEmpty()) {
            numberPositionMap.put(positions, Integer.parseInt(number.toString()));
        }

        return numberPositionMap;
    }


}
