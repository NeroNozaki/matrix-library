package matrix;

public class Matrix {
    private double[][] data;
    public boolean isSquare;

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

    public Matrix add(Matrix otherMatrix) {
        if (getRows() != otherMatrix.getRows() || getColumns() != otherMatrix.getColumns()) {
            throw new IllegalArgumentException("Matrices of different order.");
        }

        double[][] values = new double[getRows()][getColumns()];

        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getColumns(); j++) {
                values[i][j] = data[i][j] + otherMatrix.data[i][j];
            }
        }

        return new Matrix(values);
    }

    public Matrix subtract(Matrix otherMatrix) {
        return add(otherMatrix.scalarMultiply(-1));
    }

    public Matrix scalarMultiply(double scalar) {
        double[][] values = new double[getRows()][getColumns()];

        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getColumns(); j++) {
                values[i][j] = data[i][j] * scalar;
            }
        }

        return new Matrix(values);
    }

    public Matrix scalarDivide(double scalar) {
        return scalarMultiply(1 / scalar);
    }

    public Matrix multiply(Matrix otherMatrix) {
        if (getColumns() != otherMatrix.getRows()) {
            throw new IllegalArgumentException(
                "Number of columns of the first matrix must match number of rows of second matrix");
        }

        double[][] values = new double[getRows()][otherMatrix.getColumns()];
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < otherMatrix.getColumns(); j++) {
                for (int k = 0; k < otherMatrix.getRows(); k++) {
                    values[i][j] += otherMatrix.data[k][j] * data[i][k];
                } 
            }
        }

        return new Matrix(values);
    }

    public double determinant() {
        if (!isSquare()) {
            throw new IllegalStateException("Only square matrices can have determinants.");
        }

        double determinant = 0;

        if (getRows() == 1) {
            determinant = data[0][0];
        } else if (getRows() == 2) {
            determinant = (data[0][0] * data[1][1]) - (data[1][0] * data[0][1]);
        } else {
            for (int j = 0; j < getColumns(); j++) {
                if (j % 2 == 0) {
                    determinant += (makeMinor(0,j).determinant() * data[0][j]);
                } else {
                    determinant -= (makeMinor(0,j).determinant() * data[0][j]);
                }
            }
        }
        return determinant;
    }

    // -------------- Helper Functions --------------

    private Matrix makeMinor(int removedRow, int removedColumn) {
        Matrix minor = new Matrix(getRows()-1, getColumns()-1);
        int minorRow = 0;
        for (int i = 0; i < getRows(); i++) {
            if (i == removedRow) {
                continue;
            }

            int minorColumn = 0;

            for (int j = 0; j < getColumns(); j++) {
                if (j == removedColumn) {
                    continue;
                }

                minor.data[minorRow][minorColumn] = data[i][j];
                minorColumn++;
            }
            minorRow++;
        }
        return minor;
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
