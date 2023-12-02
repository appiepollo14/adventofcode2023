package nl.avasten;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Path("/day2")
public class Day2 {

    private static final int qtyRed = 12;
    private static final int qtyGreen = 13;
    private static final int qtyBlue = 14;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String calculateSum() {

        int sum = 0;
        try {
            Scanner scanner = new Scanner(new File("src/main/resources/puzzle2.txt"));

            while (scanner.hasNextLine()) {
                String currentLine = scanner.nextLine();

                int gameId = getGameId(currentLine);
                Map<String, Integer> mapped = splitString(currentLine);

                if (playable(mapped)) {
                    sum += gameId;
                }
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return String.valueOf(sum);
    }

    @GET
    @Path("/power")
    @Produces(MediaType.TEXT_PLAIN)
    public String calculatePower() {

        int sum = 0;
        try {
            Scanner scanner = new Scanner(new File("src/main/resources/puzzle2.txt"));

            while (scanner.hasNextLine()) {
                String currentLine = scanner.nextLine();

                Map<String, Integer> mapped = splitString(currentLine);

                sum += powerOfCubes(mapped);
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return String.valueOf(sum);
    }

    public static int getGameId(String text) {

        text = text.replace("Game ", "");
        int delimiterIndex = text.indexOf(":");
        int gameId = Integer.parseInt(text.substring(0, delimiterIndex));
        return gameId;
    }

    public static Map<String, Integer> splitString(String text) {
        int delimiterIndex = text.indexOf(":") + 1;
        text = text.substring(delimiterIndex);
        //Removes spaces
        text = text.replace(" ", "");
        //Split each game
        List<String> gameList = Arrays.stream(text.split(";"))
                .toList();

        List<String> perMeasurement = gameList.stream()
                .flatMap(s -> Arrays.stream(s.split(",")))
                .toList();

        Map<String, Integer> colorsCount = new HashMap<>();

        for (String measurement : perMeasurement) {
            // Split each measurement into color and value
            String color = measurement.replaceAll("\\d", "");
            int count = Integer.parseInt(measurement.replaceAll("\\D", ""));
            colorsCount.merge(color, count, Integer::max);
        }

        return colorsCount;
    }

    public static boolean playable(Map<String, Integer> input) {

        System.out.println(input.get("blue"));

        return input.get("blue") <= qtyBlue && input.get("red") <= qtyRed && input.get("green") <= qtyGreen;
    }

    public int powerOfCubes(Map<String, Integer> input) {
        return input.get("blue") * input.get("red") * input.get("green");
    }

}
