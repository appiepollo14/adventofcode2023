package nl.avasten.day8;

import io.quarkus.runtime.Startup;
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

    @Startup
    public void run() {
        System.out.println("Starting:");
        System.out.println(calculateGhostSteps());
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
    public String calculateGhostSteps() {

        long steps = 0;
        List<Node> currentNodes = getNodesEndingWithA();

        outerLoop:
        while (true) {
            for (char c : instruction.toCharArray()) {
                List<Node> temp = new ArrayList<>();
                for (Node n : currentNodes) {
                    if (c == 'L') {
                        temp.add(getNode(n.getLeftNode()));
                    } else {
                        temp.add(getNode(n.getRightNode()));
                    }
                }

                currentNodes = temp;
                steps++;

                int check = 0;
                for (Node n: currentNodes) {
                    if (n.getNodeName().endsWith("Z")) {
//                        System.out.println("Klopt!");
                        check++;
                    }
                }

//                System.out.println("Check: " + check);
                if (check == 6) {
                    break outerLoop;
                }

//                System.out.println("CurrentNodes: " + currentNodes + " , steps: " + steps);
            }
        }

        return "Steps: " + steps + " currentNode: " + currentNodes;
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
}
