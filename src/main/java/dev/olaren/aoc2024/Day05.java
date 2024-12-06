package dev.olaren.aoc2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day05 {
    public static void main(String[] args) throws IOException {
        List<OrderingRule> rules = new ArrayList<>();
        List<List<Integer>> updates = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("inputs/day05.txt"))) {
            String line = reader.readLine();

            while (!"".equals(line)) {
                String[] pageNumbers = line.split("\\|");
                int before = Integer.parseInt(pageNumbers[0]);
                int after = Integer.parseInt(pageNumbers[1]);

                rules.add(new OrderingRule(before, after));

                line = reader.readLine();
            }

            line = reader.readLine();

            while (line != null) {
                String[] pageNumbers = line.split(",");

                List<Integer> manual = Arrays.stream(pageNumbers)
                        .map(Integer::valueOf)
                        .toList();
                updates.add(manual);


                line = reader.readLine();
            }
        }

        int resultPart1 = part1(rules, updates);
        System.out.printf("[Day 5 part 1] The result is: %s\n", resultPart1);

        int resultPart2 = part2(rules, updates);
        System.out.printf("[Day 5 part 2] The result id: %s\n", resultPart2);

    }

    private static int part1(List<OrderingRule> rules, List<List<Integer>> updates) {
        int result = 0;

        for (List<Integer> update : updates) {
            if (isInOrder(update, rules)) {
                result += update.get(update.size() / 2);
            }
        }

        return result;
    }

    private static boolean isInOrder(List<Integer> update, List<OrderingRule> rules) {
        for (int i = 0; i < update.size() - 1; i++) {
            for (int y = i + 1; y < update.size(); y++) {
                for (OrderingRule rule : rules) {
                    if (update.get(i) == rule.after && update.get(y) == rule.before) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private static int part2(List<OrderingRule> rules, List<List<Integer>> updates) {
        int result = 0;

        for (List<Integer> update : updates) {
            if (!isInOrder(update, rules)) {
                ArrayList<Integer> modifiableUpdate = new ArrayList<>(update);
                modifiableUpdate.sort((a, b) -> {
                    for (OrderingRule rule : rules) {
                        if (a == rule.before && b == rule.after) {
                            return -1;
                        } else if (a == rule.after && b == rule.before) {
                            return 1;
                        }
                    }

                    return 0;
                });

                result += modifiableUpdate.get(update.size() / 2);
            }
        }

        return result;
    }

    record OrderingRule(int before, int after) {
    }
}
