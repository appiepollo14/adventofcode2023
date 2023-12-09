package nl.avasten.day7;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

@Path("/day7")
public class Day7 {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String calculateSum() {

        int win = 0;
        List<Hand> hands = linesToHands();
        Collections.sort(hands);

        for (int i = hands.size(); i >= 1; i--) {
            System.out.println("Rank: " + (i) + " , bet: " + hands.get(1000 - i).getBet() + " , chars: " + hands.get(1000 - i).getChars());
            win += (i * hands.get(1000 - i).getBet());
        }

        return "Win: " + win;
    }

    public List<Hand> linesToHands() {

        List<Hand> hands = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(new File("src/main/resources/puzzle7.txt"));

            while (scanner.hasNextLine()) {
                String currentLine = scanner.nextLine();

                String[] split = currentLine.split(" ");

                hands.add(new Hand(split[0], Integer.parseInt(split[1])));
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return hands;
    }
}
