import java.util.*;

/**
 * Created by Jack on 9/19/2016.
 *
 * Note:    The program changes the 0s to 1s as it traverses the maze
 *          This is for the purpose of keeping track of the "visited" nodes
 *          It manipulates the input as it goes, which is bad, but
 *          it also reduces the need for a second copy-maze with its own 1s and 0s
 *          to track the "visited" nodes. If manipulation of input is not desirable here
 *          it would be simple enough to switch to the two-array model and keep a second maze
 *          while leaving the input alone.
 *          I was not sure which way to go, but I acknowledge both are possible routes.
 *
 *
 */

public class Maze {

    static int[][] maze = {
            {0, 0, 1, 0},
            {0, 0, 1, 0},
            {0, 0, 0, 0}
    };

    public static void main(String[] args) {

        int[] start = {0,0};
        int[] end = {0,3};
        findShortest(start, end, maze);
    }

    static class Node {
        int x, y;
        Node parent = null;
        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public String toString() {
            return "(" + x + ", " + y + ")";
        }
    }

    public static void findShortest(int[] start, int[] end, int[][] maze) {
        Queue<Node> q = new LinkedList<Node>();
        Node root = new Node(start[0], start[1]);
        q.add(root);
        maze[root.x][root.y] = 1;

        Node current = null;

        while (!q.isEmpty()) {
            current = q.remove();

            if (current.x == end[0] && current.y == end[1]) {
                break;
            }

            maze[current.x][current.y] = 1;
            for (Node child : getChildren(current)) {
                maze[child.x][child.y] = 1;
                child.parent = current;
                q.add(child);
            }
        }

        if (current.x == end[0] && current.y == end[1]) {
            Stack<Node> path = new Stack<Node>();
            path.push(current);

            while (current.parent != null) {
                path.push(current.parent);
                current = current.parent;
            }

            while (!path.isEmpty()) {
                System.out.println(path.pop().toString());
            }
        } else {
            System.out.println("No path found.");
        }

    }

    public static List<Node> getChildren(Node parent) {
        ArrayList<Node> children = new ArrayList<Node>();
        int[][] dirs = {
                {1, 0},     // N
                {-1, 0},    // S
                {0, 1},     // E
                {0, -1}     // W
        };

        for ( int[] dir : dirs ) {
            Node child = apply(dir, parent);

            if (child.x >= 0 && child.x < maze.length && child.y >= 0 && child.y < maze[0].length && maze[child.x][child.y] != 1){
                // in-bounds, unvisited child Node
                children.add(child);
            }
        }

        return children;
    }

    public static Node apply(int[] dir, Node parent) {
        int newX = parent.x+dir[0];
        int newY = parent.y+dir[1];
        Node temp = new Node(newX, newY);

        return temp;
    }
}
