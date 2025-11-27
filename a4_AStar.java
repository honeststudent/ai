import java.util.*;

class Node {
    int x, y;   // Coordinates
    int g;      // Cost from start
    int f;      // Total cost = g + h (heuristic)

    Node(int x, int y, int g, int f) {
        this.x = x;
        this.y = y;
        this.g = g;
        this.f = f;
    }
}

// For priority queue (min-heap based on f value)
class CompareNode implements Comparator<Node> {
    public int compare(Node a, Node b) {
        return Integer.compare(a.f, b.f);
    }
}

public class a4_AStar {

    // Manhattan Distance heuristic
    private static int manhattan(int x, int y, int gx, int gy) {
        return Math.abs(x - gx) + Math.abs(y - gy);
    }

    // Check if position is inside grid and not blocked
    private static boolean isValid(int x, int y, int[][] grid) {
        int n = grid.length, m = grid[0].length;
        return (x >= 0 && x < n && y >= 0 && y < m && grid[x][y] == 0);
    }

    // A* Algorithm
    public static boolean aStar(int[][] grid, int[] start, int[] goal) {
        int n = grid.length, m = grid[0].length;

        // PriorityQueue = min-heap sorted by f value
        PriorityQueue<Node> pq = new PriorityQueue<>(new CompareNode());
        pq.add(new Node(start[0], start[1], 0, manhattan(start[0], start[1], goal[0], goal[1])));

        boolean[][] visited = new boolean[n][m];

        // 4 possible moves (up, down, left, right)
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};

        while (!pq.isEmpty()) {
            Node cur = pq.poll();

            // Skip if already visited
            if (visited[cur.x][cur.y]) continue;
            visited[cur.x][cur.y] = true;

            // If we reached the goal
            if (cur.x == goal[0] && cur.y == goal[1]) {
                System.out.println(" Goal reached at (" + cur.x + "," + cur.y + ") with cost = " + cur.g);
                return true;
            }

            // Explore neighbors
            for (int i = 0; i < 4; i++) {
                int nx = cur.x + dx[i], ny = cur.y + dy[i];
                if (isValid(nx, ny, grid) && !visited[nx][ny]) {
                    int g = cur.g + 1; // cost of move
                    int f = g + manhattan(nx, ny, goal[0], goal[1]);
                    pq.add(new Node(nx, ny, g, f));
                }
            }
        }

        System.out.println("No path found!");
        return false;
    }

    // Main method
    public static void main(String[] args) {
        int[][] grid = {
            {0,0,0,0,0},
            {1,1,1,1,0},
            {0,0,0,0,0},
            {0,1,1,1,1},
            {0,0,0,0,0}
        };

        int[] start = {0, 0};
        int[] goal = {4, 4};

        aStar(grid, start, goal);
    }
}
