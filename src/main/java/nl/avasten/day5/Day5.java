package nl.avasten;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

@Path("/day5")
public class Day5 {

    @GET
    public String calculate() {
        
        int lineNumber = 0;
        List<Long> seedList = new ArrayList<>();
        String mapType = new String;
        List<Range> ranges = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(new File("src/main/resources/puzzle5.txt"));

            while (scanner.hasNextLine()) {
                String currentLine = scanner.nextLine();
                if (lineNumber == 0) {
                    seedList = 
                }

                // Check if line is a header
                if (currentLine.contains("letters")) {
                    mapType = currentLine;
                    continue;
                }

                Range r = new Range();
                ranges.add(r);
                lineNumber++;
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return "Sum: " + sum;

    }

    private static boolean isNumber(char ch) {
        return Character.isDigit(ch);
    }

    private static List<Long> stringToSeedList(String line) {
        List<Long> seedList = new ArrayList<>();

        String sanitizedLine = line.subString(currentLine.indexOf(":"));

        StringBuilder number = new StringBuilder();
        for (int i = 0; i < sanitizedLine.length(); i++) {

            if (isNumber(sanitizedLine.charAt(i))) {
                number.append(sanitizedLine.charAt(i));
            } else {
                if (!number.isEmpty()) {
                    seedList.add(Long.parseInt(number.toString()));
                    number = new StringBuilder();
                }
            }

        }
        if (!number.isEmpty()) {
            seedList.add(Long.parseInt(number.toString()));
        }

        return seedList;
    }


}
