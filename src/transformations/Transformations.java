package transformations;
import matrix.*;

public class Transformations {
    private LinearAlgebra la = new LinearAlgebra();
    public Vector reflection2D(Vector vector, boolean flip_x, boolean flip_y) {
        Vector v = new Vector(vector);
        if (vector.dimension() != 2) {
            throw new IllegalArgumentException("vector must be 2 dimensional");
        }
        Matrix reflector_x = new Matrix(new double[][] {
        {-1, 0},
        {0, 1},
        });
        Matrix reflector_y = new Matrix(new double[][] {
        {1, 0},
        {0, -1},
        });

        if (flip_x) {
            v = la.dot(reflector_x, v);
        }
        if (flip_y) {
            v = la.dot(reflector_y, v);
        }
        return v;
    }
}
