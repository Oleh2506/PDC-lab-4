package task1;

import java.util.ArrayList;
import java.util.Collections;

public class StatisticsCalc {
    public static int calculateMin(ArrayList<Integer> numbers) {
        return Collections.min(numbers);
    }

    public static int calculateMax(ArrayList<Integer> numbers) {
        return Collections.max(numbers);
    }
    public static double calculateMean(ArrayList<Integer> numbers) {
        double sum = 0.0;
        for (int number : numbers) {
            sum += number;
        }
        return sum / numbers.size();
    }

    public static double calculateVariance(ArrayList<Integer> numbers) {
        double mean = calculateMean(numbers);
        double sumOfSquaredDifferences = 0.0;
        for (int number : numbers) {
            sumOfSquaredDifferences += Math.pow(number - mean, 2);
        }
        return sumOfSquaredDifferences / numbers.size();
    }

    public static double calculateStandardDeviation(ArrayList<Integer> numbers) {
        return Math.sqrt(calculateVariance(numbers));
    }
}
