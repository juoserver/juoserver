package net.sf.juoserver.model.npc;

import net.sf.juoserver.api.Direction;
import net.sf.juoserver.api.Point2D;
import net.sf.juoserver.model.Position;

import java.util.HashMap;
import java.util.Map;

public class NextStepPathfinding {
    // Fixed grid (0 = walkable path, 1 = obstacle)
    private static final int[][] grid = {
            { 0, 0, 0, 0 },
            { 0, 0, 0, 0 },
            { 0, 0, 0, 0 },
            { 0, 0, 0, 0 }
    };
    // Heuristic function (Manhattan distance in 3D)
    private static int heuristic(Point2D a, Point2D b) {
        return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
    }

    // Method to find the next best step from start towards end
    public static Direction findNextStep(Point2D start, Point2D end) {
        Map<Direction, Point2D> neighbors = getNeighbors(start);

        // Find the neighbor with the lowest heuristic cost to the end
        Direction bestDirection = null;
        int minHeuristic = Integer.MAX_VALUE;

        for (Map.Entry<Direction, Point2D> entry : neighbors.entrySet()) {
            Point2D neighbor = entry.getValue();
            int cost = heuristic(neighbor, end);
            if (cost < minHeuristic) {
                minHeuristic = cost;
                bestDirection = entry.getKey();
            }
        }

        // Return the direction of the best next step
        return bestDirection;
    }

    // Get valid neighbors in eight directions (North, Northeast, East, Southeast, South, Southwest, West, Northwest)
    private static Map<Direction, Point2D> getNeighbors(Point2D node) {
        Map<Direction, Point2D> neighbors = new HashMap<>();

        final int[][] directions = {
                {0, -1}, {1, -1}, {1, 0}, {1, 1},
                {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}
        };
        final Direction[] directionsNames = {
                Direction.North, Direction.Northeast, Direction.East, Direction.Southeast,
                Direction.South, Direction.Southwest, Direction.West, Direction.Northwest
        };

        for (int i = 0; i < directions.length; i++) {
            int newX = node.getX() + directions[i][0];
            int newY = node.getY() + directions[i][1];

            // Check if the new position is within bounds and walkable
            // TODO verify map size newY < grid[0].length && newX < grid.length &&
            // TODO verify obstacle grid[newX][newY] == 0
            if (newX >= 0 && newY >= 0) {
                neighbors.put(directionsNames[i], new Position(newX, newY));
            }
        }

        return neighbors;
    }
}
