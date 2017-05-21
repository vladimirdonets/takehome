package com.lenda.takehome.service.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Keeps track of every character and its neighbours on the game board.
 * Iterates like a tree to find words
 *
 * @author vdonets
 */
public class MappedWordFinder implements WordFinder {

    private final static Logger logger = LoggerFactory.getLogger(MappedWordFinder.class);

    private Map<Character, List<Node>> nodes = new HashMap<>();

    public MappedWordFinder(List<String> board) {
        List<Node[]> rows = new ArrayList<Node[]>(board.size());
        board.forEach(row -> {
            char[] r = row.toCharArray();
            Node[] nodes = new Node[r.length];
            for (int i = 0; i < r.length; i++) {
                nodes[i] = new Node(r[i]);
            }
            rows.add(nodes);
        });

        for (int y = 0; y < rows.size(); y++) {
            for (int x = 0; x < rows.get(y).length; x++) {
                populate(x, y, rows);
            }
        }
    }

    /**
     * Populates a node and links to its neighbours
     *
     * @param x    column number
     * @param y    row number
     * @param rows rows of game board
     */
    private void populate(int x, int y, List<Node[]> rows) {
        Node node = nodeAt(x, y, rows);
        if (node == null)
            return;
        if (logger.isTraceEnabled())
            logger.trace("populating node " + node + " at [" + x + ", " + y + "]");
        if (nodes.containsKey(node.c)) {
            nodes.get(node.c).add(node);
        } else {
            List<Node> cNodes = new ArrayList<>();
            cNodes.add(node);
            nodes.put(node.c, cNodes);
        }
        Node left = nodeAt(x - 1, y, rows);
        Node right = nodeAt(x + 1, y, rows);
        Node top = nodeAt(x, y + 1, rows);
        Node bottom = nodeAt(x, y - 1, rows);
        if (left != null)
            node.addNeighbour(left);
        if (right != null)
            node.addNeighbour(right);
        if (top != null)
            node.addNeighbour(top);
        if (bottom != null)
            node.addNeighbour(bottom);

    }

    /**
     * Finds a node at given coordinates
     *
     * @param x    column number
     * @param y    row number
     * @param rows rows of game board
     * @return a node at x,y or or null if such a node does not exist
     */
    private Node nodeAt(int x, int y, List<Node[]> rows) {
        if (y >= 0 && y < rows.size()) {
            Node[] row = rows.get(y);
            if (x >= 0 && x < row.length) {
                if (logger.isTraceEnabled())
                    logger.trace("[" + x + ", " + y + "] = " + row[x]);
                return row[x];
            }

        }
        if (logger.isTraceEnabled())
            logger.trace("[" + x + ", " + y + "] = empty");
        return null;
    }

    @Override
    public boolean isValid(String word) {
        if (logger.isDebugEnabled())
            logger.debug("searching for [" + word + "]");
        List<Node> roots = nodes.get(word.charAt(0));
        for (Node root : roots)
            if (searchTree(word, 1, root))
                return true;
        return false;
    }

    /**
     * Searches starting from root for character at index startIdx
     *
     * @param word     word we are ultimately searching for
     * @param startIdx index of character to find next
     * @param root     node to start search from
     * @return true if word is findable
     */
    private boolean searchTree(String word, int startIdx, Node root) {
        if (logger.isTraceEnabled())
            logger.trace("visiting [" + root + "], searching startIdx ["
                    + startIdx + "] in [" + word + "]");
        List<Node> neighbours = root.neighbours.get(word.charAt(startIdx));
        if (neighbours != null) {
            if (startIdx == word.length() - 1)
                return true;
            else
                for (Node neighbour : neighbours) {
                    if (searchTree(word, startIdx + 1, neighbour))
                        return true;
                }

        }
        return false;
    }

    /**
     * A node in the board. One character with all its neighbours. Used
     * to search for words character by character in all of a given nodes
     * neighbours.
     */
    private class Node {

        @Override
        public String toString() {
            return "Node{" +
                    "char = " + c + "; neighbours = " + neighbours.keySet() +
                    '}';
        }

        private Node(char c) {
            this.c = c;
        }

        private Map<Character, List<Node>> neighbours = new HashMap<>();
        private char c;

        private void addNeighbour(Node node) {
            if (neighbours.containsKey(node.c)) {
                neighbours.get(node.c).add(node);
            } else {
                List<Node> cNodes = new ArrayList<>();
                cNodes.add(node);
                neighbours.put(node.c, cNodes);
            }
        }
    }
}
