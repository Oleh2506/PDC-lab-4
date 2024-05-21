package task2;

import java.util.Random;

public class Tester {
    public static void main(String[] args) {
        int matrixSize = 256;
        int threadCount = 8;
        int minValue = -10;
        int maxValue = 10;
        double averageFJTime = 0.0;
        double averageStripeTime = 0.0;
        double averageSpeedUp;
        int experimentCount = 20;

        for (int i = 0; i < experimentCount; i++) {
            Matrix matrixA = new Matrix(generateRandomMatrix(matrixSize, matrixSize, minValue, maxValue));
            Matrix matrixB = new Matrix(generateRandomMatrix(matrixSize, matrixSize, minValue, maxValue));

            Matrix stripeMatrixC = MatrixMultiplier.stripeMultiply(matrixA, matrixB, threadCount);
            Matrix forkJoinMatrixC = MatrixMultiplier.forkJoinMultiply(matrixA, matrixB, matrixSize / threadCount);

            if (!stripeMatrixC.isEqual(forkJoinMatrixC)) {
                throw new IllegalArgumentException("Matrix multiplication gave a false result!");
            }

            System.out.println("Test " + (i + 1));
            System.out.println("Stripe time: " + stripeMatrixC.getCalcTime());
            System.out.println("ForkJoin time: " + forkJoinMatrixC.getCalcTime());
            averageStripeTime += stripeMatrixC.getCalcTime();
            averageFJTime += forkJoinMatrixC.getCalcTime();
            System.out.println();
        }

        averageStripeTime /= experimentCount;
        averageFJTime /= experimentCount;
        averageSpeedUp = averageStripeTime / averageFJTime;
        System.out.println("Average speedup: " + averageSpeedUp);
    }

    public static int[][] generateRandomMatrix(int rowCount, int columnCount, int minValue, int maxValue) {
        int[][] matrix = new int[rowCount][columnCount];
        Random rand = new Random();

        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                matrix[i][j] = rand.nextInt((maxValue - minValue) + 1) + minValue;
            }
        }

        return matrix;
    }
}
