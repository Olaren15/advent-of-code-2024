package dev.olaren.aoc2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.IntStream;

public class Day04 {
    private static final int[] XMAS_LETTERS = {'M', 'A', 'S'};

    public static void main(String[] args) throws IOException {
        int[][] letters;

        try (BufferedReader reader = new BufferedReader(new FileReader("inputs/day04.txt"))) {
            letters = reader.lines()
                    .map(String::chars)
                    .map(IntStream::toArray)
                    .toArray(int[][]::new);
        }

        int part1Result = part1(letters);
        System.out.printf("[Day 4 part 1] The amount of XMAS is: %s\n", part1Result);

        int part2Result = part2(letters);
        System.out.printf("[Day 4 part 2] The amount of X-MAS is: %s\n", part2Result);
    }

    private static int part1(int[][] letters) {
        int result = 0;

        for (int x = 0; x < letters.length; x++) {
            for (int y = 0; y < letters[x].length; y++) {
                if (letters[x][y] != 'X') {
                    continue;
                }

                for (Direction direction : Direction.values()) {
                    if (foundXmas(letters, new Point(x, y), direction)) {
                        result++;
                    }
                }
            }
        }

        return result;
    }

    private static int part2(int[][] letters) {
        int result = 0;

        for (int x = 1; x < letters.length - 1; x++) {
            for (int y = 1; y < letters[x].length - 1; y++) {
                if (letters[x][y] != 'A') {
                    continue;
                }

                Point current = new Point(x, y);
                Point northEast = getPointForDirection(letters, current, Direction.NorthEast);
                Point southEast = getPointForDirection(letters, current, Direction.SouthEast);
                Point southWest = getPointForDirection(letters, current, Direction.SouthWest);
                Point northWest = getPointForDirection(letters, current, Direction.NorthWest);

                boolean diagonal1ContainsMas = (
                        letters[northWest.x][northWest.y] == 'M' && letters[southEast.x][southEast.y] == 'S'
                )
                        || (
                        letters[northWest.x][northWest.y] == 'S' && letters[southEast.x][southEast.y] == 'M'
                );

                boolean diagonal2ContainsMas = (
                        letters[northEast.x][northEast.y] == 'M' && letters[southWest.x][southWest.y] == 'S'
                )
                        || (
                        letters[northEast.x][northEast.y] == 'S' && letters[southWest.x][southWest.y] == 'M'
                );

                if (diagonal1ContainsMas && diagonal2ContainsMas) {
                    result++;
                }
            }
        }

        return result;
    }

    private static boolean foundXmas(int[][] letters, Point point, Direction direction) {
        Point currentPoint = getPointForDirection(letters, point, direction);

        for (int letter : XMAS_LETTERS) {
            if (currentPoint == null) {
                return false;
            }

            if (letter != letters[currentPoint.x][currentPoint.y]) {
                return false;
            }

            currentPoint = getPointForDirection(letters, currentPoint, direction);
        }

        return true;
    }

    private static Point getPointForDirection(int[][] letters, Point point, Direction direction) {
        Point newPoint = switch (direction) {
            case North -> new Point(point.x, point.y - 1);
            case NorthEast -> new Point(point.x + 1, point.y - 1);
            case East -> new Point(point.x + 1, point.y);
            case SouthEast -> new Point(point.x + 1, point.y + 1);
            case South -> new Point(point.x, point.y + 1);
            case SouthWest -> new Point(point.x - 1, point.y + 1);
            case West -> new Point(point.x - 1, point.y);
            case NorthWest -> new Point(point.x - 1, point.y - 1);
        };

        if (newPoint.x < 0 || newPoint.x >= letters.length || newPoint.y < 0 || newPoint.y >= letters[newPoint.x].length) {
            return null;
        }

        return newPoint;
    }

    enum Direction {
        North,
        NorthEast,
        East,
        SouthEast,
        South,
        SouthWest,
        West,
        NorthWest;
    }

    record Point(int x, int y) {
    }
}
