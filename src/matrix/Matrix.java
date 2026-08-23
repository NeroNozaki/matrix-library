package matrix;

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
        this.data = new double[rows][columns];
    }
    public Matrix(Matrix matrix) {
        this.data = new double[matrix.getRows()][matrix.getColumns()];
        for (int i = 0; i < matrix.getRows(); i++) {
            for (int j = 0; j < matrix.getColumns(); j++) {
                this.data[i][j] = matrix.get(i+1, j+1);
            }
        }
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
        String matrix = "";

        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < data[i].length; j++) {
                matrix += data[i][j] + " ";
            }
            matrix += "\n";
        }

        return matrix;
    }
}
