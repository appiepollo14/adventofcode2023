package nl.avasten.day10;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@Path("/day10")
public class Day10 {

    static int startPuntRij;
    static int startPuntKolom;
    static Map<Integer, Map<Integer, Character>> mappedInput = new HashMap<>();

    static {
        mapInput();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String calculateSteps() {

        int steps = 0;
        System.out.println("Startpunt rij: " + startPuntRij + ", kolom: " + startPuntKolom);
        System.out.println("Mapped input: " + mappedInput);

        determineStartKind();

        return "Sequences: " + steps;
    }

    public static String determineStartKind() {
        //Naar boven?
        char b = mappedInput.get(startPuntRij - 1).get(startPuntKolom);
        boolean boven = (b == '|' || b == '7' || b == 'F');
        //Naar links?
        char l = mappedInput.get(startPuntRij).get(startPuntKolom - 1);
        boolean links = (l == '-' || l == 'L' || l == 'F');
        //Naar onder?
        char o = mappedInput.get(startPuntRij + 1).get(startPuntKolom);
        boolean onder = (o == '|' || o == 'L' || o == 'J');
        //Naar rechts?
        char r = mappedInput.get(startPuntRij).get(startPuntKolom + 1);
        boolean rechts = (l == '-' || l == 'J' || l == '7');

        System.out.println("Boven: " + boven + " , onder: " + onder + " , links: " + links + " , rechts: " + rechts);
        return new String();
    }

    public static boolean goLeft(char input) {
        return false;
    }

    public static void mapInput() {

        try {
            Scanner scanner = new Scanner(new File("src/main/resources/puzzle10.txt"));

            int lineNum = 0;
            while (scanner.hasNextLine()) {
                Map<Integer, Character> mappedLine = new HashMap<>();
                String currentLine = scanner.nextLine();
                for (int i = 0; i < currentLine.length(); i++) {
                    mappedLine.put(i, currentLine.charAt(i));
                    if (currentLine.charAt(i) == 'S') {
                        startPuntRij = lineNum;
                        startPuntKolom = i;
                    }
                }
                mappedInput.put(lineNum, mappedLine);
                lineNum++;
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
