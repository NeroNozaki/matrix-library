package matrix;
import java.text.DecimalFormat;

public class Matrix {
    private double[][] data;

    public Matrix(double[][] data) {
        if (data.length > 1) {
            for (int i = 1; i < data.length; i++) {
                if (data[i].length != data[i-1].length) {
                    throw new IllegalArgumentException("Matrix is not rectangular.");
                }
            }
        }

        this.data = new double[data.length][data[0].length];
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                this.data[i][j] = data[i][j];
            }
        }

    }

    public Matrix(int rows, int columns) {
        if (rows > 0 && columns > 0) {
            this.data = new double[rows][columns];
        } else {
            throw new IllegalArgumentException("rows or columns cannot have length smaller than 1");
        }
    }

    public Matrix(Matrix matrix) {
        this.data = new double[matrix.getRows()][matrix.getColumns()];
        for (int i = 0; i < matrix.getRows(); i++) {
            for (int j = 0; j < matrix.getColumns(); j++) {
                this.data[i][j] = matrix.get(i+1, j+1);
            }
        }
    }

    // ------ row and column operations ------
    public Matrix swapRows(int rowA, int rowB) {
        Matrix M = new Matrix(this);
        for (int j = 1; j <= M.getColumns(); j++) {
            double value = M.get(rowA, j);
            M.set(rowA, j, M.get(rowB, j));
            M.set(rowB, j, value);
        }
        return M;
    }

    public Matrix swapColumns(int columnA, int columnB) {
        Matrix M = new Matrix(this);
        for (int i = 1; i <= M.getRows(); i++) {
            double value = M.get(i, columnA);
            M.set(i, columnA, M.get(i, columnB));
            M.set(i, columnB, value);
        }
        return M;
    }

    public Matrix scaleRow(int row, double scalar) {
        if (scalar == 0) {
            throw new IllegalArgumentException("Scalar must be non-zero.");
        }

        Matrix M = new Matrix(this);
        for (int j = 1; j <= M.getColumns(); j++) {
            M.set(row, j, M.get(row, j) * scalar);
        }
        return M;
    }

    public Matrix scaleColumn(int column, double scalar) {
        if (scalar == 0) {
            throw new IllegalArgumentException("Scalar must be non-zero.");
        }

        Matrix M = new Matrix(this);
        for (int i = 1; i <= M.getRows(); i++) {
            M.set(i, column, M.get(i, column) * scalar);
        }
        return M;
    }

    public Matrix addRowMultiple(
        int targetRow, int sourceRow, double scalar, int startColumn) {
        Matrix M = new Matrix(this);
        for (int j = startColumn; j <= M.getColumns(); j++) {
            M.set(targetRow, j, M.get(targetRow, j) + M.get(sourceRow, j) * scalar);
        }
        return M;
    }

    public Matrix addColumnMultiple(int targetColumn, int sourceColumn, double scalar) {
        Matrix M = new Matrix(this);
        for (int i = 1; i <= M.getRows(); i++) {
            M.set(i, targetColumn, M.get(i, targetColumn) + M.get(i, sourceColumn) * scalar);
        }
        return M;
    }

    // ------------ Turn a column matrix into a vector
    public Vector vectorize() {
        if (this.getColumns() != 1) {
            throw new IllegalArgumentException("Can only turn column matrices into vectors.");
        }

        Vector v = new Vector(this.getRows());
        for (int i = 1; i <= this.getRows(); i++) {
            v.set(i, this.get(i, 1));
        }
        return v;
    }

    // ------------ GETTERS/SETTERS -------------

    public double get(int row, int column) {
        if (row > getRows() || row < 1 || column > getColumns() || column < 1) {
            throw new IllegalArgumentException("Invalid matrix position.");
        }

        return data[row-1][column-1];
    }
    public void set(int row, int column, double value) {
        if (row > getRows() || row < 1 || column > getColumns() || column < 1) {
            throw new IllegalArgumentException("Invalid matrix position.");
        }

        data[row-1][column-1] = value;
    }
    public int getRows() {
        return data.length;
    }
    public int getColumns() {
        return data[0].length;
    }
    private boolean isSquare() {
        return getRows() == getColumns();
    }

    // --------------- Override ---------------
    @Override
    public String toString() {
        DecimalFormat format = new DecimalFormat("0.00");

        String[][] formatted = new String[getRows()][getColumns()];
        int[] widths = new int[getColumns()];

        // Format all numbers and find the widest value in each column.
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getColumns(); j++) {
                double value = data[i][j];
                if (value == 0.0) {
                    value = 0.0;
                }

                formatted[i][j] = format.format(value);

                if (formatted[i][j].length() > widths[j]) {
                    widths[j] = formatted[i][j].length();
                }
            }
        }

        StringBuilder matrix = new StringBuilder();

        // Print each value using the width calculated for its column.
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getColumns(); j++) {
                matrix.append(String.format("%" + widths[j] + "s", formatted[i][j]));

                if (j < getColumns() - 1) {
                    matrix.append("  ");
                }
            }
            matrix.append("\n");
        }

        return matrix.toString();
    }
}
