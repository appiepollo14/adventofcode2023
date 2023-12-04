package nl.avasten.day4;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

@Path("/day4")
public class Day4 {

    Map<Integer, Card> listOfCards = new HashMap<>();
    Map<Integer, Integer> copies = new HashMap<>();

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String calculateSum() {

        int sum = 0;
        int cardNum = 1;
        try {
            Scanner scanner = new Scanner(new File("src/main/resources/puzzle4.txt"));

            while (scanner.hasNextLine()) {
                String currentLine = scanner.nextLine();

                listOfCards.put(cardNum, mapStringToCard(currentLine));
                copies.put(cardNum, 1);
                cardNum++;
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for (Card c : listOfCards.values()) {
            sum += pointCalculator(c);
        }


        return String.valueOf(sum);
    }

    @GET
    @Path("/copies")
    @Produces(MediaType.TEXT_PLAIN)
    public String calculateCopySum() {

        int sumB = 0;

        for (Map.Entry<Integer, Card> c : listOfCards.entrySet()) {

            int currentCopies = copies.get(c.getKey());
            int winners = c.getValue().getWinners();
            for (int i = 1; i <= winners; i++) {
                int newAmount = copies.get(c.getKey() + i) + currentCopies;
                copies.put(c.getKey() + i, newAmount);
            }
        }

        for (Integer i : copies.values()) {
            sumB += i;
        }
        return "Hoi: " + sumB;
    }

    private int pointCalculator(Card c) {

        System.out.println("Mine: " + c.getMyNumbers());
        System.out.println("Winning: " + c.getWinningNumbers());
        int matches = countMatchingObjects(c.getWinningNumbers(), c.getMyNumbers());
        c.setWinners(matches);

        if (matches == 0) {
            return 0;
        } else if (matches == 1) {
            System.out.println("1 Match");
            return 1;
        } else {
            System.out.println("aantal : " + matches + " matches");
            int punten = Double.valueOf(Math.pow(2, matches - 1)).intValue();
            System.out.println("Aantal punten: " + punten);
            return punten;
        }
    }

    private static int countMatchingObjects(List<Integer> winning, List<Integer> mine) {

        // Count the number of matching objects
        int matchingCount = 0;
        for (Integer i : mine) {
            if (winning.contains(i)) {
                matchingCount++;
            }
        }
        return matchingCount;
    }

    private Card mapStringToCard(String currentLine) {

        // Removes Card prefix
        currentLine = currentLine.replace("Card ", "");
        int delimiterIndex = currentLine.indexOf(":");
        int cardId = Integer.parseInt(currentLine.substring(0, delimiterIndex).replaceAll(" ", ""));
        String winning = currentLine.substring(delimiterIndex, currentLine.indexOf("|"));
        String mine = currentLine.substring(currentLine.indexOf("|") + 1);

        List<Integer> winningNumbers = stringToListOfNumbers(winning);

        List<Integer> myNumbers = stringToListOfNumbers(mine);

        return new Card(cardId, winningNumbers, myNumbers);
    }


    private static boolean isNumber(char ch) {
        return Character.isDigit(ch);
    }

    private static List<Integer> stringToListOfNumbers(String currentLine) {

        StringBuilder number = new StringBuilder();
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < currentLine.length(); i++) {

            if (isNumber(currentLine.charAt(i))) {
                number.append(currentLine.charAt(i));
            } else {
                if (!number.isEmpty()) {
                    numbers.add(Integer.parseInt(number.toString()));
                    number = new StringBuilder();
                }
            }

        }
        if (!number.isEmpty()) {
            numbers.add(Integer.parseInt(number.toString()));
        }

        return numbers;
    }
}
