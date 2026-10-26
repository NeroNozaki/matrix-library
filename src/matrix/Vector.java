package matrix;
import java.security.InvalidParameterException;

public class Vector {
    private Matrix data;
    private double norm;


    public Vector(double[] elements) {
        double[][] data = new double[elements.length][1];
        for (int i = 0; i < elements.length; i++) {
            data[i][0] = elements[i];
        }

        assignFields(data);
    }

    public Vector(int dimension) {
        double[][] data = new double[dimension][1];
        assignFields(data);
    }

    public Vector(Vector vector) {
        double[][] data = new double[vector.dimension()][1];
        for (int i = 1; i <= vector.dimension(); i++) {
            data[i-1][0] = vector.get(i);
        }
        assignFields(data);
    }

    private void assignFields(double[][] elements) {
        this.data = new Matrix(elements);
        this.norm = calculateNorm();
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

    public int dimension() { return data.getRows(); }

    public double getNorm() { return this.norm; }

    // ---------------- Operations ------------------

    private double calculateNorm() {
        double sum = 0;
        for (int i = 1; i <= this.dimension(); i++) {
            sum += Math.pow(this.get(i), 2);
        }

        return Math.sqrt(sum);
    }

    public Vector scale(double scalar) {
        Vector scaled = new Vector(this.dimension());
        for (int i = 1; i <= this.dimension(); i++) {
            scaled.set(i, this.get(i) * scalar);
        }

        return scaled;
    }

    public Vector sum(Vector vector) {
        if (this.dimension() != vector.dimension()) {
            throw new IllegalArgumentException("Can only sum vectors of the same dimension.");
        }

        Vector result = new Vector(this.dimension());

        for (int i = 1; i <= this.dimension(); i++) {
            result.set(i, this.get(i) + vector.get(i));
        }

        return result;
    }

    public Vector subtract(Vector vector) {
        if (this.dimension() != vector.dimension()) {
            throw new IllegalArgumentException("Can only subtract vectors of the same dimension.");
        }

        Vector result = new Vector(this.dimension());

        for (int i = 1; i <= this.dimension(); i++) {
            result.set(i, this.get(i) - vector.get(i));
        }

        return result;
    }

    public double dot(Vector vector) {
        if (this.dimension() != vector.dimension()) {
            throw new IllegalArgumentException("Both vectors need to have the same dimension.");
        }
        double sum = 0;
        for (int i = 1; i <= this.dimension(); i++) {
            sum += this.get(i) * vector.get(i);
        }

        return sum;
    }

    public double angleBetween(Vector vector) {
        if (this.getNorm() == 0 || vector.getNorm() == 0) {
            throw new IllegalArgumentException("Norm or neither vector can be 0.");
        }

        double cosine = this.dot(vector) / (this.getNorm() * vector.getNorm());
        return Math.acos(cosine);
    }

    public Vector projectOn(Vector vector) {
        double factor = this.dot(vector) / vector.getNorm();

        return this.scale(factor);
    }

    public double distanceBetween(Vector vector) {
        Vector v = new Vector(this.subtract(this.projectOn(vector)));

        return v.getNorm();
    }

    // --------------- Override --------------
    @Override
    public String toString() {
        return data.toString();
    }
}
