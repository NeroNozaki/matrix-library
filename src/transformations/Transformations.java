package transformations;
import matrix.*;

// NOTE: LEMBRE-SE DE USAR COORDENADAS HOMOGENEAS!!!

public class Transformations {
    private LinearAlgebra la = new LinearAlgebra();
    private D2 twoD = new D2();
    private D3 threeD = new D3();

    public Vector reflection2D(Vector vector, boolean reflect_x, boolean reflect_y) {
        if (vector.dimension() != 2) {
            throw new IllegalArgumentException("Can only handle 2-dimensional vectors");
        }

        Vector v = homogenizeVector(vector, 1);

        if (reflect_x) {
            v = la.dot(twoD.reflector_x, v);
        }
        if (reflect_y) {
            v = la.dot(twoD.reflector_y, v);
        }

        return dehomogenizeVector(v);
    }
    public Vector reflection3D(
        Vector vector, boolean reflect_x, boolean reflect_y, boolean reflect_z) {
        if (vector.dimension() != 3) {
            throw new IllegalArgumentException("Can only handle 3-dimensional vectors");
        }

        Vector v = homogenizeVector(vector, 1);

        if (reflect_x) {
            v = la.dot(threeD.reflector_x, v);
        }
        if (reflect_y) {
            v = la.dot(threeD.reflector_y, v);
        }
        if (reflect_z) {
            v = la.dot(threeD.reflector_z, v);
        }

        return dehomogenizeVector(v);
    }

    // method to create a vector in homogenous coordinates. appending 1 at the end.
    private Vector homogenizeVector(Vector vector, double level) {
        Vector v = new Vector(vector.dimension()+1);

        for (int i = 1; i <= v.dimension()-1; i++) {
            v.set(i, vector.get(i));
        }
        v.set(v.dimension(), level);

        return v;
    }

    // method to dehomogenize a vector, bringing it to cartesian coordinates.
    private Vector dehomogenizeVector(Vector vector) {
        double level = vector.get(vector.dimension());
        Vector v = new Vector(vector.dimension()-1);

        for (int i = 1; i <= v.dimension(); i++) {
            v.set(i, vector.get(i)/level);
        }

        return v;
    }
}

class D2 {
    public D2() {}

    Matrix reflector_x = new Matrix(new double[][] {
    {1, 0, 0},
    {0, -1, 0},
    {0, 0, 1},
    });

    Matrix reflector_y = new Matrix(new double[][] {
    {-1, 0, 0},
    {0, 1, 0},
    {0, 0, 1},
    });

}

class D3 {
    D3() {}

    Matrix reflector_x = new Matrix(new double[][] {
    {1, 0, 0, 0},
    {0, -1, 0, 0},
    {0, 0, -1, 0},
    {0, 0, 0, 1},
    });

    Matrix reflector_y = new Matrix(new double[][] {
    {-1, 0, 0, 0},
    {0, 1, 0, 0},
    {0, 0, -1, 0},
    {0, 0, 0, 1},
    });

    Matrix reflector_z = new Matrix(new double[][] {
    {-1, 0, 0, 0},
    {0, -1, 0, 0},
    {0, 0, 1, 0},
    {0, 0, 0, 1},
    });

}
