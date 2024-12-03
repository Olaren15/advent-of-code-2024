package dev.olaren.aoc2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day02 {
    public static void main(String[] args) throws IOException {
        List<List<Integer>> reports;
        try (BufferedReader reader = new BufferedReader(new FileReader("inputs/day02.txt"))) {
            reports = reader.lines()
                    .map(line -> Arrays.stream(line.split(" "))
                            .map(Integer::valueOf)
                            .toList()
                    )
                    .toList();
        }

        long safeReports = part1(reports);
        System.out.printf("[Day 2 part 1] The number of safe reports is: %d\n", safeReports);

        long safeReportsV2 = part2(reports);
        System.out.printf("[Day 2 part 2] The number of safe reports is: %d\n", safeReportsV2);
    }

    private static long part1(List<List<Integer>> reports) {
        return reports.stream()
                .filter(Day02::isReportSafe)
                .count();
    }

    private static long part2(List<List<Integer>> reports) {
        return reports.stream()
                .filter(Day02::isReportSafeV2)
                .count();
    }

    private static boolean isReportSafe(List<Integer> report) {
        boolean increasing = false;
        boolean decreasing = false;

        for (int i = 1; i < report.size(); i++) {
            int difference = report.get(i - 1) - report.get(i);

            if (difference >= 1 && difference <= 3) {
                if (increasing) {
                    return false;
                }

                decreasing = true;
            } else if (difference <= -1 && difference >= -3) {
                if (decreasing) {
                    return false;
                }

                increasing = true;
            } else {
                return false;
            }
        }

        return true;
    }

    private static boolean isReportSafeV2(List<Integer> report) {
        if (isReportSafe(report)) {
            return true;
        }

        for (int removedIndex = 0; removedIndex < report.size(); removedIndex++) {
            List<Integer> reportWithoutIndex = new ArrayList<>(report);
            reportWithoutIndex.remove(removedIndex);

            if (isReportSafe(reportWithoutIndex)) {
                return true;
            }
        }

        return false;
    }
}
