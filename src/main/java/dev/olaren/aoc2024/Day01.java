package dev.olaren.aoc2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;

import static java.util.stream.Collectors.*;

public class Day01 {
    public static void main(String[] args) throws IOException {
        List<Long> left = new ArrayList<>();
        List<Long> right = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("inputs/day01.txt"))) {
            String line = reader.readLine();

            while (line != null) {
                String[] parts = line.split("\\s+");

                left.add(Long.valueOf(parts[0]));
                right.add(Long.valueOf(parts[1]));

                line = reader.readLine();
            }
        }

        long distance = part1(new ArrayList<>(left), new ArrayList<>(right));
        System.out.printf("[Day 1 part 1] The distance is : %d\n", distance);

        long similarity = part2(new ArrayList<>(left), new ArrayList<>(right));
        System.out.printf("[Day 1 part 2] The similarity is: %d\n", similarity);
    }

    private static long part1(List<Long> left, List<Long> right) {
        left.sort(Comparator.naturalOrder());
        right.sort(Comparator.naturalOrder());

        long distance = 0;
        assert left.size() == right.size();
        for (int i = 0; i < left.size(); i++) {
            long lineDistance = Math.abs(left.get(i) - right.get(i));
            distance += lineDistance;
        }

        return distance;
    }

    private static long part2(List<Long> left, List<Long> right) {
        Map<Long, Long> appearancesRight = right.stream().collect(groupingBy(Function.identity(), counting()));

        Map<Long, Long> similarityScores = appearancesRight.entrySet().stream()
                .map(entry ->
                        new AbstractMap.SimpleEntry<>(
                                entry.getKey(),
                                entry.getKey() * entry.getValue()
                        )
                )
                .collect(toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue));

        return left.stream()
                .map(similarityScores::get)
                .filter(Objects::nonNull)
                .mapToLong(x -> x)
                .sum();
    }
}