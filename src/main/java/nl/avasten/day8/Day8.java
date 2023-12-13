package nl.avasten.day8;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

@Path("/day8")
public class Day8 {

    static String instruction;
    static Map<String, Node> nodes = new HashMap<>();

    static {
        mapInput();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String calculateSteps() {

        int steps = 0;
        Node currentNode = getNode("AAA");

        outerLoop:
        while (true) {
            for (char c : instruction.toCharArray()) {
                if (c == 'L') {
                    currentNode = getNode(currentNode.getLeftNode());

                } else {
                    currentNode = getNode(currentNode.getRightNode());
                }

                steps++;
                if (Objects.equals(currentNode.getNodeName(), "ZZZ")) {
                    break outerLoop;
                }
            }
        }

        return "Steps: " + steps + " currentNode: " + currentNode.getNodeName();
    }


    @GET
    @Path("/b")
    @Produces(MediaType.TEXT_PLAIN)
    public String calculateGhostStepsNielsWay() {

        List<Node> startNodes = getNodesEndingWithA();

        List<Integer> listOfSteps = new ArrayList<>();

        for (Node n : startNodes) {

            int steps = 0;
            Node currentNode = n;

            outerLoop:
            while (true) {
                for (char c : instruction.toCharArray()) {
                    if (c == 'L') {
                        currentNode = getNode(currentNode.getLeftNode());
                    } else {
                        currentNode = getNode(currentNode.getRightNode());
                    }

                    steps++;
                    if (currentNode.getNodeName().endsWith("Z")) {
                        listOfSteps.add(steps);
                        break outerLoop;
                    }
                }
            }
        }

//        https://berekening.net/rekenmachine-van-het-kleinste-gemene-veelvoud solved, de array niet, hoe dan?

        return "Steps: " + findLCMOfArray(listOfSteps);
    }

    public Node getNode(String n) {
        return nodes.get(n);
    }

    public List<Node> getNodesEndingWithA() {
        String suffix = "A";
        return nodes.entrySet().stream()
                .filter(entry -> entry.getKey().endsWith(suffix)).map(Map.Entry::getValue)
                .toList();
    }

    public static void mapInput() {

        try {
            Scanner scanner = new Scanner(new File("src/main/resources/puzzle8.txt"));

            int lineNum = 1;

            while (scanner.hasNextLine()) {
                String currentLine = scanner.nextLine();

                if (lineNum == 1) {
                    instruction = currentLine;
                    lineNum++;
                    continue;
                }

                if (lineNum == 2) {
                    lineNum++;
                    continue;
                }
                currentLine = currentLine.replace("(", "");
                currentLine = currentLine.replace(")", "");
                currentLine = currentLine.replaceAll(" ", "");
                int i = currentLine.indexOf("=");
                String curNode = currentLine.substring(0, i);
                String[] s = currentLine.substring(i + 1).split(",");
//                System.out.println(currentLine + " CurNode: " + curNode + " split: " + s[0] + " , " + s[1]);

                nodes.put(curNode, new Node(curNode, s[0], s[1]));
                lineNum++;
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    // Methode om de grootste gemene deler (greatest common divisor - gcd) te berekenen
    private static int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    // Methode om de kleinste gemene veelvoud (least common multiple - lcm) te berekenen
    private static int lcm(int a, int b) {
        return (a * b) / gcd(a, b);
    }

    // Methode om de kleinste gemene veelvoud van een lijst met getallen te berekenen
    private static long findLCMOfArray(List<Integer> arr) {
        int result = arr.get(0);
        for (int i = 1; i < arr.size(); i++) {
            result = lcm(result, arr.get(i));
        }
        return result;
    }

//    public static int gcd(int a, int b) {
//        if (b == 0) {
//            return a;
//        }
//        return gcd(b, a % b);
//    }
//
//    // Function to find the LCM of an array of numbers
//    public static int findLCM(List<Integer> arr) {
//        int lcm = arr.get(0);
//        for (int i = 1; i < arr.size(); i++) {
//            int currentNumber = arr.get(i);
//            lcm = (lcm * currentNumber) / gcd(lcm, currentNumber);
//        }
//        return lcm;
//    }
}
