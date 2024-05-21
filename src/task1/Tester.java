package task1;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Tester {
    public static void main(String[] args) throws IOException {
        performSpeedUpTest();
    }

    public static void performBasicTest() throws IOException {
        Folder folder = Folder.fromDirectory(new File("books"));

        HashMap<Integer, Integer> wordsByLengthsCount = WordCounter.countTotalWordsByLengthsFJ(folder);

        wordsByLengthsCount.forEach((key, value) -> System.out.printf("length: %-2s - word number: %s%n", key, value));
        ArrayList<Integer> values = new ArrayList<>(wordsByLengthsCount.values());

        for (var val : values) {
            System.out.println(val);
        }

        System.out.println("\nStatistics");
        System.out.println("Min: " + StatisticsCalc.calculateMin(values));
        System.out.println("Max: " + StatisticsCalc.calculateMax(values));
        System.out.println("Mean: " + StatisticsCalc.calculateMean(values));
        System.out.println("Variance: " + StatisticsCalc.calculateVariance(values));
        System.out.println("Standard deviation: " + StatisticsCalc.calculateStandardDeviation(values));
    }

    public static  void performSpeedUpTest() throws IOException {
        Folder folder = Folder.fromDirectory(new File("books"));
        double averageSequentialTime = 0.0;
        double averageFJTime = 0.0;
        double averageSpeedUp;
        int testCount = 20;

        for (int i = 0; i < testCount; i++) {
            System.out.println("Test " + (i + 1));

            long startTime = System.nanoTime();
            WordCounter.countTotalWordsByLengthsOnSingleThread(folder);
            long endTime = System.nanoTime();
            System.out.println("Sequential time: " + (endTime - startTime) / 1000000000.0);
            averageSequentialTime += (endTime - startTime) / 1000000000.0;

            startTime = System.nanoTime();
            WordCounter.countTotalWordsByLengthsFJ(folder);
            endTime = System.nanoTime();
            System.out.println("ForkJoin time: " + (endTime - startTime) / 1000000000.0);
            averageFJTime += (endTime - startTime) / 1000000000.0;

            System.out.println();
        }

        averageSequentialTime /= testCount;
        averageFJTime /= testCount;
        averageSpeedUp = averageSequentialTime / averageFJTime;

        System.out.println("Average speedup: " + averageSpeedUp);
    }
}
