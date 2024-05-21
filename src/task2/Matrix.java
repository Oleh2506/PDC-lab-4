package task2;

public class Matrix {
    private final int[][] matrix;
    private double calcTime = 0.0;

    public void setCalcTime(double calcTime) {
        this.calcTime = calcTime;
    }

    public double getCalcTime() {
        return this.calcTime;
    }

    public Matrix(int[][] matrix) {
        this.matrix = matrix;
    }

    public Matrix(int rowNum, int colNum) { this.matrix = new int [rowNum][colNum]; }

    public int getRowCount() { return this.matrix.length; }

    public int getColumnCount() { return this.matrix[0].length; }

    public int getElement(int i, int j) { return this.matrix[i][j]; }

    public void setElement(int i, int j, int value) { this.matrix[i][j] = value; }

    public void print() {
        for (int[] row : this.matrix) {
            for (int val : row) {
                System.out.print(val + " ");
            }
            System.out.println();
        }
    }

    boolean isEqual(Matrix m) {
        if (m.getColumnCount() != this.getColumnCount() || m.getRowCount() != this.getRowCount()) {
            return false;
        }

        for (int i = 0; i < m.getRowCount(); i++) {
            for (int j = 0; j < m.getColumnCount(); j++) {
                if (m.getElement(i, j) != this.getElement(i, j)) {
                    return false;
                }
            }
        }

        return true;
    }

    public Matrix getStripe(int startRow, int endRow) {
        Matrix stripe = new Matrix(endRow - startRow, this.getColumnCount());

        for (int i = startRow; i < endRow; i++) {
            for (int j = 0; j < this.getColumnCount(); j++) {
                stripe.setElement(i - startRow, j, this.getElement(i, j));
            }
        }

        return stripe;
    }

    public Matrix getConcatenatedByRows(Matrix matrixB) {
        if (this.getColumnCount() != matrixB.getColumnCount()) {
            throw new IllegalArgumentException("Matrices must have the same number of columns!");
        }

        Matrix concatenatedMatrix = new Matrix(this.getRowCount() + matrixB.getRowCount(), this.getColumnCount());

        for (int j = 0; j < this.getColumnCount(); j++) {
            for (int i = 0; i < this.getRowCount(); i++) {
                concatenatedMatrix.setElement(i, j, this.getElement(i, j));
            }

            for (int i = this.getRowCount(); i < this.getRowCount() + matrixB.getRowCount(); i++) {
                concatenatedMatrix.setElement(i, j, matrixB.getElement(i - this.getRowCount(), j));
            }
        }

        return concatenatedMatrix;
    }
}
