package nl.avasten.day8;

public class Node {

    private String nodeName;
    private String leftNode;
    private String rightNode;

    public Node(String nodeName, String leftNode, String rightNode) {
        this.nodeName = nodeName;
        this.leftNode = leftNode;
        this.rightNode = rightNode;
    }

    public String getRightNode() {
        return rightNode;
    }

    public String getLeftNode() {
        return leftNode;
    }

    public String getNodeName() {
        return nodeName;
    }

    @Override
    public String toString() {
        return "Node{" +
                "currentNode='" + nodeName + '\'' +
                ", leftNode='" + leftNode + '\'' +
                ", rightNode='" + rightNode + '\'' +
                '}';
    }
}
