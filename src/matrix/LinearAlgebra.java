package matrix;

import java.security.InvalidParameterException;

public class LinearAlgebra {

    public Matrix transpose(Matrix A) {
        Matrix B = new Matrix(A.getColumns(), A.getRows());
        for (int i = 1; i <= A.getRows(); i++) {
            for (int j = 1; j <= A.getColumns(); j++) {
                B.set(j, i, A.get(i, j));
            }
        }

        return B;
    }

    // --------------- sum -------------------
    public Matrix sum(Matrix A, Matrix B) {
        if (A.getRows() != B.getRows() || A.getColumns() != B.getColumns()) {
            throw new IllegalArgumentException("Matrices of different order.");
        }

        double[][] values = new double[A.getRows()][A.getColumns()];

        for (int i = 1; i <= A.getRows(); i++) {
            for (int j = 1; j <= A.getColumns(); j++) {
                values[i-1][j-1] = A.get(i, j) + B.get(i, j);
            }
        }

        return new Matrix(values);
    }
    public Vector sum(Vector a, Vector b) {
        if (a.dimension() != b.dimension()) {
            throw new InvalidParameterException("Vectors must be of the same dimension.");
        }

        double[] result = new double[a.dimension()];

        for (int i = 1; i <= a.dimension(); i++) {
            result[i-1] = a.get(i) + b.get(i);
        }
        return new Vector(result);
    }

    // --------------- times -------------------
    public Matrix times(double scalar, Matrix matrix) {
        double[][] values = new double[matrix.getRows()][matrix.getColumns()];

        for (int i = 1; i <= matrix.getRows(); i++) {
            for (int j = 1; j <= matrix.getColumns(); j++) {
                values[i-1][j-1] = matrix.get(i, j) * scalar;
            }
        }

        return new Matrix(values);
    }
    public Vector times(double scalar, Vector vector) {
        double[] values = new double[vector.dimension()];

        for (int i = 1; i <= vector.dimension(); i++) {
            values[i-1] = vector.get(i) * scalar;
        }

        return new Vector(values);
    }
    public Matrix times(Matrix A, Matrix B) {
        if (A.getRows() != B.getRows() || A.getColumns() != B.getColumns()) {
            throw new InvalidParameterException("Matrices must have the same order.");
        }

        double[][] values = new double[A.getRows()][A.getColumns()];

        for (int i = 1; i <= A.getRows(); i++) {
            for (int j = 1; j <= A.getColumns(); j++) {
                values[i-1][j-1] = A.get(i, j) * B.get(i, j);
            }
        }

        return new Matrix(values);
    }
    public Vector times(Vector a, Vector b) {
        if (a.dimension() != b.dimension()) {
            throw new InvalidParameterException("Vectors must have the same dimension.");
        }

        double[] values = new double[a.dimension()];

        for (int i = 1; i <= a.dimension(); i++) {
            values[i-1] = a.get(i) * b.get(i);
        }

        return new Vector(values);
    }

    // --------------- dot -------------------
    public Matrix dot(Matrix A, Matrix B) {
        if (A.getColumns() != B.getRows()) {
            throw new IllegalArgumentException(
                "Number of columns of the first matrix must match number of rows of second matrix");
        }

        double[][] values = new double[A.getRows()][B.getColumns()];
        for (int i = 1; i <= A.getRows(); i++) {
            for (int j = 1; j <= B.getColumns(); j++) {
                for (int k = 1; k <= B.getRows(); k++) {
                    values[i-1][j-1] += B.get(k, j) * A.get(i, k);
                } 
            }
        }

        return new Matrix(values);
    }
    public Vector dot(Vector a, Vector b) {
        return times(a, b);
    }
    public Matrix dot(Matrix A, Vector b) {
        if (A.getColumns() != b.dimension()) {
            throw new InvalidParameterException("Columns of matrix and dimension of vector don't match.");
        }

        Matrix B = new Matrix(b.dimension(), 1);
        for (int i = 1; i < b.dimension(); i++) {
            B.set(i, 1, b.get(i));
        }
        
        return dot(A, B);
    }
    public Matrix dot(Vector a, Matrix B) {
        if (a.dimension() != B.getRows()) {
            throw new InvalidParameterException("Dimension of vector and rows of matrix don't match.");
        }

        Matrix A = new Matrix(1, a.dimension());
        for (int i = 1; i < a.dimension(); i++) {
            A.set(1, i, a.get(i));
        }
        
        return dot(A, B);
    }

    // ------------- gauss ---------------
    public Matrix gauss(Matrix A) {
        int pivotRow = 1;
        int column = 1;
        Matrix B = new Matrix(A);
        int swapRow;
        double pivot;
        double factor;

        int maxPivots = Math.min(B.getRows(), B.getColumns());
        while (pivotRow <= maxPivots && column <= maxPivots) {
            swapRow = findSwapRow(B, pivotRow, column);
            B = new Matrix(swapRows(B, swapRow, pivotRow));
            pivot = B.get(pivotRow, column);
            if (Math.abs(pivot) < 0.0000000000001) {
                pivotRow++;
                column++;
                continue;
            }


            for (int i = pivotRow + 1; i <= B.getRows(); i++) {
                factor = B.get(i, column) / pivot;
                for (int j = column; j <= B.getColumns(); j++) {
                    B.set(i, j, B.get(i, j) - (B.get(pivotRow, j) * factor));
                }
            }

            pivotRow++;
            column++;
        }

        return B;
    }
    private int findSwapRow(Matrix A, int pivotRow, int column) {
        int swapRow = pivotRow;
        double max = A.get(pivotRow, column);
        for (int i = pivotRow; i <= A.getRows(); i++) {
            if (Math.abs(A.get(i, column)) > Math.abs(max)) {
                max = A.get(i, column);
                swapRow = i;
            }
        }
        return swapRow;
    }
    private Matrix swapRows(Matrix A, int swapRow, int targetRow) {
        Matrix B = new Matrix(A.getRows(), A.getColumns());
        for (int i = 1; i <= B.getRows(); i++) {
            for (int j = 1; j <= B.getColumns(); j++) {
                if (i == targetRow) {
                    B.set(i, j, A.get(swapRow, j));
                } else if (i == swapRow) {
                    B.set(i, j, A.get(targetRow, j));
                } else {
                    B.set(i, j, A.get(i, j));
                }
            }
        }
        return B;
    }
}
