public class Matrix {
    private double[][] data;

    public Matrix(double[][] data) {

        if (getRows() > 1) {
            for (int i = 1; i < getRows(); i++) {
                if (data[i].length != data[i-1].length) {
                    throw new IllegalArgumentException("Matrix is not rectangular.");
                }
            }
        }

        this.data = data;
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

    // public Matrix subtract(Matrix otherMatrix) {
        
    // }

    // public Matrix scalarMultiply(double num) {
        
    // }

    // public Matrix multiply(Matrix otherMatrix) {
        
    // }

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

    public int getRows() {
        return data.length;
    }

    public int getColumns() {
        return data[0].length;
    }

}
