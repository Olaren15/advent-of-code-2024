package dev.olaren.aoc2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Day03 {
    public static void main(String[] args) throws IOException {
        List<String> lines;
        try (BufferedReader reader = new BufferedReader(new FileReader("inputs/day03.txt"))) {
            lines = reader.lines().toList();
        }

        long part1 = Day03.part1(lines);
        System.out.printf("[Day 3 part 1] The result is: %d\n", part1);

        long part2 = Day03.part2(lines);
        System.out.printf("[Day 3 part 2] The result is: %d\n", part2);
    }

    public static long part1(List<String> lines) {
        Pattern pattern = Pattern.compile("mul\\((\\d+),(\\d+)\\)");
        return lines.stream()
                .flatMap(line -> {
                    Matcher matcher = pattern.matcher(line);
                    Stream.Builder<MatchResult> builder = Stream.builder();

                    while (matcher.find()) {
                        builder.add(matcher.toMatchResult());
                    }

                    return builder.build();
                })
                .map(result -> {
                    long a = Long.parseLong(result.group(1));
                    long b = Long.parseLong(result.group(2));

                    return a * b;
                })
                .mapToLong(x -> x)
                .sum();
    }

    public static long part2(List<String> lines) {
        Pattern patternMul = Pattern.compile("mul\\((\\d+),(\\d+)\\)");

        String instructions = String.join("", lines);
        String instructionsEnabled = instructions.replaceAll("don't\\(\\).*?((do\\(\\))|$)", "");

        Matcher matcher = patternMul.matcher(instructionsEnabled);

        long sum = 0;
        while (matcher.find()) {
            MatchResult result = matcher.toMatchResult();

            long a = Long.parseLong(result.group(1));
            long b = Long.parseLong(result.group(2));

            sum += a * b;
        }

        return sum;
    }
}
