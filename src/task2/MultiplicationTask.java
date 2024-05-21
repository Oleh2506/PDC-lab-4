package task2;

import java.util.concurrent.RecursiveTask;

public class MultiplicationTask extends RecursiveTask<Matrix> {
    private final int threshold;
    private final Matrix matrixA;
    private final Matrix matrixB;

    public MultiplicationTask(int threshold, Matrix matrixA, Matrix matrixB) {
        this.threshold = threshold;
        this.matrixA = matrixA;
        this.matrixB = matrixB;
    }

    @Override
    protected Matrix compute() {
        int endRow = matrixA.getRowCount();

        if (endRow <= threshold) {
            return MatrixMultiplier.sequentialMultiply(matrixA, matrixB);
        }
        else {
            RecursiveTask<Matrix> firstHalfTask = new MultiplicationTask(threshold,
                    matrixA.getStripe(0, endRow / 2), matrixB);
            firstHalfTask.fork();

            RecursiveTask<Matrix> secondHalfTask = new MultiplicationTask(threshold,
                    matrixA.getStripe(endRow / 2, endRow), matrixB);
            secondHalfTask.fork();

            return firstHalfTask.join().getConcatenatedByRows(secondHalfTask.join());
        }
    }
}
