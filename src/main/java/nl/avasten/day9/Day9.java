package nl.avasten.day9;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@Path("/day9")
public class Day9 {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String calculateSteps() {

        List<List<Integer>> sequencelist = mapInput();

        for (List<Integer> l : sequencelist) {
            calcDif(l);
        }

        return "Sequences: " + sequencelist;
    }

    public static void calcDif(List<Integer> input) {
        List<Integer> diff = new ArrayList<>();
        if (input.size() <= 1) {
            System.out.println("TOO SMALL");
        } else {
            for (int i = 1; i < input.size(); i++) {
                int cur = input.get(i);
                int prev = input.get(i - 1);
                diff.add(cur - prev);
            }
        }

        if (diff.get(0) == 0) {
            System.out.println(diff);
            System.out.println("STOP");
            return;
        } else {
            calcDif(diff);
        }

        System.out.println(diff);
    }


    public static List<List<Integer>> mapInput() {
        List<List<Integer>> sequences = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(new File("src/main/resources/puzzle9.txt"));

            while (scanner.hasNextLine()) {
                String currentLine = scanner.nextLine();
                String[] s = currentLine.split(" ");
                sequences.add(Arrays.stream(s).map(Integer::valueOf).toList());
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return sequences;

    }
}
