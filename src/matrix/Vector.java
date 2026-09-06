package matrix;

import java.security.InvalidParameterException;

public class Vector {
    private Matrix data;

    public Vector(double[] elements) {
        double[][] data = new double[elements.length][1];
        for (int i = 0; i < elements.length; i++) {
            data[i][0] = elements[i];
        }

        this.data = new Matrix(data);
    }

    public Vector(int dimension) {
        double[][] data = new double[dimension][1];
        this.data = new Matrix(data);
    }

    // ------------------- GETTERS/SETTERS ------------------
    public double get(int index) {
        if (index <= 0) {
            throw new InvalidParameterException("Index must be greater than 0.");
        }
        return data.get(index, 1);
    }
    public void set(int index, double value) {
        if (index <= 0) {
            throw new InvalidParameterException("Index must be greater than 0.");
        }
        data.set(index, 1, value);
    }
    public int dimension() {
        return data.getRows();
    }

    // --------------- Override --------------
    @Override
    public String toString() {
        return data.toString();
    }
}
