package nl.avasten;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

@Path("/day3")
public class Day3 {

    static final int lineSize = 140;
    static Map<Integer, List<Integer>> symbolsPerLine = new HashMap<>();
    static Map<Integer, String> mappedInput = inputToMap();
    static Map<Integer, Map<List<Integer>, Integer>> numbersPerLine = new HashMap<>();

    @GET
    @Path("/test")
    public String testInput() {
        int sum = 0;

        //Map symbol Positions
        for (int i = 0; i < mappedInput.size(); i++) {
            List<Integer> symbolList = getSymbolPositions(mappedInput.get(i));
            symbolsPerLine.put(i, symbolList);
        }

        // Get numbers per line,
        for (int i = 0; i < mappedInput.size(); i++) {
            numbersPerLine.put(i, getNumberPositionMap(mappedInput.get(i)));
        }

        // check adjecening fields for each number, if found, add to sum
        for (int i = 0; i < numbersPerLine.size(); i++) {
            Map<List<Integer>, Integer> numbersAndPosition = numbersPerLine.get(i);
            // First row
            if (i == 0) {
                // Loop through each number
                for (List<Integer> positions : numbersAndPosition.keySet()) {
                    int number = numbersAndPosition.get(positions);

                    if (checkSameLine(i, positions) || checkLineBelow(i, positions)) {
                        System.out.println("Line: " + i + " , number: " + number + " , hit: true");
                        sum += number;
                    } else {
                        System.out.println("Line: " + i + " , number: " + number + " , hit: false");
                    }
                }

                // Last row
            } else if (i == numbersPerLine.size() - 1) {
                for (List<Integer> positions : numbersAndPosition.keySet()) {
                    int number = numbersAndPosition.get(positions);

                    if (checkSameLine(i, positions) || checkLineAbove(i, positions)) {
                        System.out.println("Line: " + i + " , number: " + number + " , hit: true");
                        sum += number;
                    } else {
                        System.out.println("Line: " + i + " , number: " + number + " , hit: false");
                    }
                }
                // All other rows
            } else {
                for (List<Integer> positions : numbersAndPosition.keySet()) {
                    int number = numbersAndPosition.get(positions);
                    //System.out.println("Numv: " + k);
                    if (checkSameLine(i, positions) || checkLineBelow(i, positions) || checkLineAbove(i, positions)) {
                        System.out.println("Line: " + i + " , number: " + number + " , hit: true");
                        sum += number;
                    } else {
                        System.out.println("Line: " + i + " , number: " + number + " , hit: false");
                    }
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

    private static List<Integer> getSymbolPositions(String currentLine) {
        List<Integer> symbolPositions = new ArrayList<>();

        for (int i = 0; i < currentLine.length(); i++) {
            if (isSymbol(currentLine.charAt(i))) {
                symbolPositions.add(i);
            }

            //System.out.println("Char : " + currentLine.charAt(i) + " , " + isSymbol(currentLine.charAt(i)));

        }
        return symbolPositions;

    }

    private static boolean checkSameLine(int lineNumber, List<Integer> positions) {
        // Get left from number:
//        System.out.println(" Posit: " + positions);

        int left = Collections.min(positions) - 1;
        if (left > 0) {
            if (symbolsPerLine.get(lineNumber).contains(left)) {
                return true;
            }
        }

        int right = Collections.max(positions) + 1;
//        System.out.println(" right: " + right);
        if (right < lineSize) {
            if (symbolsPerLine.get(lineNumber).contains(right)) {
//                System.out.println("symbol found");
                return true;
            }
        }

        return false;
    }

    private static boolean checkLineBelow(int lineNumber, List<Integer> positions) {

        int lineNumberBelow = lineNumber + 1;

        // Get leftUnder from number:
        int left = Collections.min(positions) - 1;
        if (left > 0) {
            if (symbolsPerLine.get(lineNumberBelow).contains(left)) {
                return true;
            }
        }

        // Get rightUnder
        int right = Collections.max(positions) + 1;
        if (right < lineSize) {
            if (symbolsPerLine.get(lineNumberBelow).contains(right)) {
                return true;
            }
        }

        //All below
        boolean found = false;
        for (int pos : positions) {
            if (symbolsPerLine.get(lineNumberBelow).contains(pos)) {
                found = true;
                break;
            }
        }

        return found;
    }

    private static boolean checkLineAbove(int lineNumber, List<Integer> positions) {

        int lineNumberAbove = lineNumber - 1;

        // Get leftAbove from number:
        int left = Collections.min(positions) - 1;
        if (left > 0) {
            if (symbolsPerLine.get(lineNumberAbove).contains(left)) {
                return true;
            }
        }

        // Get rightAbove
        int right = Collections.max(positions) + 1;
        if (right < lineSize) {
            if (symbolsPerLine.get(lineNumberAbove).contains(right)) {
                return true;
            }
        }

        //All above
        boolean found = false;
        for (int pos : positions) {
            if (symbolsPerLine.get(lineNumberAbove).contains(pos)) {
                found = true;
                break;
            }
        }

        return found;
    }

//    private static boolean isSymbol(char ch) {
//        return !Character.isLetter(ch) && !Character.isDigit(ch) && !Character.isSpaceChar(ch);
//    }
//
//    private static boolean isNumber(char ch) {
//        return !isSymbol(ch);
//    }

    private static boolean isSymbol(char ch) {
        return !Character.isLetter(ch) && !Character.isDigit(ch) && !Character.isSpaceChar(ch);
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
//        System.out.println(numberPositionMap);

        return numberPositionMap;
    }


}
