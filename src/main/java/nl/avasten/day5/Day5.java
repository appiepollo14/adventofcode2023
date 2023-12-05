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
        List<Long> seedList = new ArrayList<>();

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

        List<Long> locations = new ArrayList<>();

        for (Long i: seedList) {
            locations.add(humidityToLocation(temperatureToHumidity(lightToTemperature(waterToLight(fertilizerToWater(soilToFertilizer(seedToSoil(i))))))));
        }

        System.out.println(locations);

        return "Min loc: " + Collections.min(locations);

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

    private long fertilizerToWater(Long fertilizer) {
        List<Range> fertilizerToWaterRanges = ranges.get("fertilizer-to-water");
        Long water;
        for (Range r : fertilizerToWaterRanges) {
            if (fertilizer >= r.getSourceStart() && fertilizer <= r.getSourceEnd()) {
//                System.out.println("Found range: " + r);
                Long diff = fertilizer - r.getSourceStart();
//                System.out.println("Diff: " + diff);
                water = r.getDestinationStart() + diff;
//                System.out.println("Soil: " + soil);
                return water;
            }
        }

        return fertilizer;
    }

    private long waterToLight(Long water) {
        List<Range> waterToLightRanges = ranges.get("water-to-light");
        Long light;
        for (Range r : waterToLightRanges) {
            if (water >= r.getSourceStart() && water <= r.getSourceEnd()) {
//                System.out.println("Found range: " + r);
                Long diff = water - r.getSourceStart();
//                System.out.println("Diff: " + diff);
                light = r.getDestinationStart() + diff;
//                System.out.println("Soil: " + soil);
                return light;
            }
        }

        return water;
    }

    private long lightToTemperature(Long light) {
        List<Range> waterToLightRanges = ranges.get("light-to-temperature");
        Long temperature;
        for (Range r : waterToLightRanges) {
            if (light >= r.getSourceStart() && light <= r.getSourceEnd()) {
//                System.out.println("Found range: " + r);
                Long diff = light - r.getSourceStart();
//                System.out.println("Diff: " + diff);
                temperature = r.getDestinationStart() + diff;
//                System.out.println("Soil: " + soil);
                return temperature;
            }
        }

        return light;
    }

    private long temperatureToHumidity(Long temperature) {
        List<Range> waterToLightRanges = ranges.get("temperature-to-humidity");
        Long humidity;
        for (Range r : waterToLightRanges) {
            if (temperature >= r.getSourceStart() && temperature <= r.getSourceEnd()) {
//                System.out.println("Found range: " + r);
                Long diff = temperature - r.getSourceStart();
//                System.out.println("Diff: " + diff);
                humidity = r.getDestinationStart() + diff;
//                System.out.println("Soil: " + soil);
                return humidity;
            }
        }

        return temperature;
    }

    private long humidityToLocation(Long humidity) {
        List<Range> waterToLightRanges = ranges.get("humidity-to-location");
        Long location;
        for (Range r : waterToLightRanges) {
            if (humidity >= r.getSourceStart() && humidity <= r.getSourceEnd()) {
//                System.out.println("Found range: " + r);
                Long diff = humidity - r.getSourceStart();
//                System.out.println("Diff: " + diff);
                location = r.getDestinationStart() + diff;
//                System.out.println("Soil: " + soil);
                return location;
            }
        }

        return humidity;
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
