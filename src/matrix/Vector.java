package matrix;

import java.security.InvalidParameterException;

public class Vector {
    private double[] data;

    public Vector(double[] data) {
        this.data = data;
    }

    // ------------------- GETTERS/SETTERS ------------------
    public double get(int index) {
        if (index <= 0) {
            throw new InvalidParameterException("Index must be greater than 0.");
        }
        return data[index-1];
    }
    public void set(int index, double value) {
        if (index <= 0) {
            throw new InvalidParameterException("Index must be greater than 0.");
        }
        this.data[index-1] = value;
    }
    public int dimension() {
        return data.length;
    }

    // --------------- Override --------------
    @Override
    public String toString() {
        String vector = "";

        for (int i = 0; i < dimension(); i++) {
            vector += data[i] + " ";
        }

        return vector += "\n";
    }
}
