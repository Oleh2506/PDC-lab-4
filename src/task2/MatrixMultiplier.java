package task2;

import java.util.ArrayList;
import java.util.concurrent.ForkJoinPool;

public class MatrixMultiplier {
    private static final ForkJoinPool forkJoinPool = new ForkJoinPool();

    public static boolean areMatricesValid(Matrix matrixA, Matrix matrixB) {
        return matrixA.getColumnCount() == matrixB.getRowCount();
    }

    public static Matrix sequentialMultiply(Matrix matrixA, Matrix matrixB) {
        if (!areMatricesValid(matrixA, matrixB)) {
            throw new IllegalArgumentException("Matrices cannot be multiplied: incompatible dimensions");
        }

        Matrix matrixC = new Matrix(matrixA.getRowCount(), matrixB.getColumnCount());

        long startTime = System.nanoTime();
        for (int i = 0; i < matrixA.getRowCount(); i++) {
            for (int j = 0; j < matrixB.getColumnCount(); j++) {
                for (int k = 0; k < matrixA.getColumnCount(); k++) {
                    matrixC.setElement(i, j,
                            matrixC.getElement(i, j) + matrixA.getElement(i, k) * matrixB.getElement(k, j));
                }
            }
        }
        long endTime = System.nanoTime();
        matrixC.setCalcTime((endTime - startTime) / 1000000000.0);

        return matrixC;
    }

    public static Matrix stripeMultiply(Matrix matrixA, Matrix matrixB, int threadCount) {
        if (!areMatricesValid(matrixA, matrixB)) {
            throw new IllegalArgumentException("Matrices cannot be multiplied: incompatible dimensions");
        }

        Matrix matrixC = new Matrix(matrixA.getRowCount(), matrixB.getColumnCount());

        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0; i < threadCount; i++) {
            int threadNumber = i;
            threads.add(new Thread(() -> processThread(matrixA, matrixB, matrixC, threadNumber,
                    (int) Math.ceil((double) matrixA.getRowCount() / threadCount))));
        }

        long startTime = System.nanoTime();
        for (Thread t : threads) {
            t.start();
        }

        try {
            for (Thread t : threads) {
                t.join();
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        long endTime = System.nanoTime();
        matrixC.setCalcTime((endTime - startTime) / 1000000000.0);

        return matrixC;
    }

    private static void processThread(Matrix matrixA, Matrix matrixB, Matrix matrixC, int threadNumber, int rowsPerThread) {
        for (int i = threadNumber * rowsPerThread; i < (threadNumber + 1) * rowsPerThread && i < matrixA.getRowCount(); i++) {
            for (int j = 0; j < matrixB.getColumnCount(); j++) {
                for (int k = 0; k < matrixB.getRowCount(); k++) {
                    matrixC.setElement(i, j,
                            matrixC.getElement(i, j) + matrixA.getElement(i, k) * matrixB.getElement(k, j));
                }
            }
        }
    }

    public static Matrix forkJoinMultiply(Matrix matrixA, Matrix matrixB, int threshold) {

        long startTime = System.nanoTime();
        Matrix matrixC = forkJoinPool.invoke(new MultiplicationTask(threshold, matrixA, matrixB));
        long endTime = System.nanoTime();
        matrixC.setCalcTime((endTime - startTime) / 1000000000.0);


        return matrixC;
    }
}
