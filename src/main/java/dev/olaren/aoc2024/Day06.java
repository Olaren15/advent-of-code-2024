package dev.olaren.aoc2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;

public class Day06 {
    public static void main(String[] args) throws IOException {
        int[][] grid;
        try (BufferedReader reader = new BufferedReader(new FileReader("inputs/day06.txt"))) {
            grid = reader.lines()
                    .map(String::chars)
                    .map(IntStream::toArray)
                    .toArray(int[][]::new);
        }

        int[][] copy = Arrays.stream(grid).map(int[]::clone).toArray(int[][]::new);
        long resultPart1 = part1(copy);
        System.out.printf("[Day 6 part 1] The result is %d\n", resultPart1);

        long resultPart2 = part2(grid);
        System.out.printf("[Day 6 part 2] The result is %d\n", resultPart2);
    }

    private static long part1(int[][] grid) {
        Point position = null;
        Direction facingDirection = Direction.Up;

        for (int y = 0; y < grid.length && position == null; y++) {
            for (int x = 0; x < grid[y].length && position == null; x++) {
                if (grid[y][x] == '^') {
                    position = new Point(x, y);
                }
            }
        }

        long total = 0;

        while (true) {
            if (grid[position.y][position.x] != 'X') {
                grid[position.y][position.x] = 'X';
                total++;
            }

            Optional<Point> nextPosition = getPointForDirection(grid, position, facingDirection);
            if (nextPosition.isEmpty()) {
                break;
            }

            if (grid[nextPosition.get().y][nextPosition.get().x] == '#') {
                facingDirection = rotateClockwize(facingDirection);
                continue;
            }

            position = nextPosition.get();
        }


        return total;
    }

    private static long part2(int[][] grid) {
        // aaaaaaaaaaaaaaarrrgg
        return 0;
    }

    private static Optional<Point> getPointForDirection(int[][] grid, Point point, Direction direction) {
        Point newPoint = switch (direction) {
            case Up -> new Point(point.x, point.y - 1);
            case Right -> new Point(point.x + 1, point.y);
            case Down -> new Point(point.x, point.y + 1);
            case Left -> new Point(point.x - 1, point.y);
        };

        if (newPoint.y < 0 || newPoint.y >= grid.length || newPoint.x < 0 || newPoint.x >= grid[newPoint.y].length) {
            return Optional.empty();
        }

        return Optional.of(newPoint);
    }

    private static Direction rotateClockwize(Direction direction) {
        return switch (direction) {
            case Up -> Direction.Right;
            case Right -> Direction.Down;
            case Down -> Direction.Left;
            case Left -> Direction.Up;
        };
    }

    enum Direction {
        Up,
        Down,
        Left,
        Right
    }

    record Point(int x, int y) {
    }
}
