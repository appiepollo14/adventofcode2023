package nl.avasten.day5;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

@Path("/day5")
public class Day5 {

    Map<String, List<Range>> ranges = new HashMap<>();

    @GET
    public String calculate() {

        int lineNumber = 0;
        List<Long> seedList;

        try {
            Scanner scanner = new Scanner(new File("src/main/resources/puzzle5.txt"));

            List<Range> rangeList = new ArrayList<>();
            String mapType = new String();

            while (scanner.hasNextLine()) {
                String currentLine = scanner.nextLine();
                if (lineNumber == 0) {
                    seedList = stringToSeedList(currentLine);
                    lineNumber++;
                    continue;
                }


                // Check if line is a header
                if (currentLine.contains(":")) {
                    if (!rangeList.isEmpty()) {
                        ranges.put(mapType, rangeList);
                        rangeList = new ArrayList<>();
                    }
                    mapType = currentLine.substring(0, currentLine.indexOf(" "));
                    lineNumber++;
                    continue;
                }

                if (currentLine.isBlank() || currentLine.isEmpty()) {
                    lineNumber++;
                    continue;
                }

                rangeList.add(stringToRange(mapType, currentLine));
                lineNumber++;
            }

            ranges.put(mapType, rangeList);

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println(ranges.toString());

        Long seed = 26921883L;

        Long soil = seedToSoil(seed);

        Long fertilzer = soilToFertilizer(soil);

        System.out.println(fertilzer);

        return "Sum: " + null;

    }

    private long seedToSoil(Long seed) {
        List<Range> seedToSoilRanges = ranges.get("seed-to-soil");
        Long soil;
        for (Range r : seedToSoilRanges) {
            if (seed >= r.getSourceStart() && seed <= r.getSourceEnd()) {
                System.out.println("Found range: " + r);
                Long diff = seed - r.getSourceStart();
                System.out.println("Diff: " + diff);
                soil = r.getDestinationStart() + diff;
                System.out.println("Soil: " + soil);
                return soil;
            }
        }

        return seed;
    }

    private long soilToFertilizer(Long soil) {
        List<Range> soilToFertilizerRanges = ranges.get("soil-to-fertilizer");
        Long fertilizer;
        for (Range r : soilToFertilizerRanges) {
            if (soil >= r.getSourceStart() && soil <= r.getSourceEnd()) {
//                System.out.println("Found range: " + r);
                Long diff = soil - r.getSourceStart();
//                System.out.println("Diff: " + diff);
                fertilizer = r.getDestinationStart() + diff;
//                System.out.println("Soil: " + soil);
                return fertilizer;
            }
        }

        return soil;
    }

    private static boolean isNumber(char ch) {
        return Character.isDigit(ch);
    }

    private static List<Long> stringToSeedList(String line) {
        List<Long> seedList = new ArrayList<>();

        String sanitizedLine = line.substring(line.indexOf(":") + 1);

        StringBuilder number = new StringBuilder();
        for (int i = 0; i < sanitizedLine.length(); i++) {

            if (isNumber(sanitizedLine.charAt(i))) {
                number.append(sanitizedLine.charAt(i));
            } else {
                if (!number.isEmpty()) {
                    seedList.add(Long.parseLong(number.toString()));
                    number = new StringBuilder();
                }
            }

        }
        if (!number.isEmpty()) {
            seedList.add(Long.parseLong(number.toString()));
        }

        return seedList;
    }

    public Range stringToRange(String type, String input) {

        String[] pieces = input.split(" ");
        return new Range(type, Long.parseLong(pieces[0]), Long.parseLong(pieces[1]), Long.parseLong(pieces[2]));
    }


}
