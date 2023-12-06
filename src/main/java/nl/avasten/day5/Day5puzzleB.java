package nl.avasten.day5;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

@Path("/day5/b")
public class Day5puzzleB {

    Map<String, List<Range>> ranges = new HashMap<>();

    public Long calculate(String line) {

        String[] pieces = line.split(" ");
        Long startSeed = null;
        Long endSeed;

        long location = Long.MAX_VALUE;


        for (int i = 0; i < pieces.length; i++) {
            if (i % 2 == 0) {
                startSeed = Long.parseLong(pieces[i]);
            } else {
                endSeed = startSeed + Long.parseLong(pieces[i]) - 1;
                System.out.println("Startseed: " + startSeed + " , endseed: " + endSeed);
                for (Long l = startSeed; l <= endSeed; l++) {
                    long r = humidityToLocation(temperatureToHumidity(lightToTemperature(waterToLight(fertilizerToWater(soilToFertilizer(seedToSoil(l)))))));
                    if (r < location) {
                        location = r;
                    }

                }
            }
        }

        System.out.println("Min loc: " + location);

        return location;
    }


    @GET
    public String start() {

        int lineNumber = 0;
        Map<Long, Long> seedRanges = new HashMap<>();

        String seedRow = new String();

        try {
            Scanner scanner = new Scanner(new File("src/main/resources/puzzle5.txt"));

            List<Range> rangeList = new ArrayList<>();
            String mapType = new String();

            while (scanner.hasNextLine()) {
                String currentLine = scanner.nextLine();
                if (lineNumber == 0) {
                    seedRow = currentLine.substring(currentLine.indexOf(":") + 2);
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

        return "Min loc: " + calculate(seedRow);

    }

    private long seedToSoil(Long seed) {
        List<Range> seedToSoilRanges = ranges.get("seed-to-soil");
        Long soil;
        for (Range r : seedToSoilRanges) {
            if (seed >= r.getSourceStart() && seed <= r.getSourceEnd()) {
//                System.out.println("Found range: " + r);
                Long diff = seed - r.getSourceStart();
//                System.out.println("Diff: " + diff);
                soil = r.getDestinationStart() + diff;
//                System.out.println("Soil: " + soil);
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
                Long diff = soil - r.getSourceStart();
                fertilizer = r.getDestinationStart() + diff;
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

    public Range stringToRange(String type, String input) {

        String[] pieces = input.split(" ");
        return new Range(type, Long.parseLong(pieces[0]), Long.parseLong(pieces[1]), Long.parseLong(pieces[2]));
    }


}
