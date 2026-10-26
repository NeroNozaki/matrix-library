package transformations;
import matrix.*;

// NOTE: LEMBRE-SE DE USAR COORDENADAS HOMOGENEAS!!!

public class Transformations {
    private LinearAlgebra la = new LinearAlgebra();
    private D2 twoD = new D2();
    private D3 threeD = new D3();

    // ====================== Reflection ======================

    public Vector reflection2DX(Vector vector) {
        return reflection2D(vector, true, false);
    }

    public Vector reflection2DY(Vector vector) {
        return reflection2D(vector, false, true);
    }

    private Vector reflection2D(Vector vector, boolean reflect_x, boolean reflect_y) {
        checkVector(vector, 2);

        Vector v = homogenizeVector(vector, 1);

        if (reflect_x) {
            v = la.dot(twoD.reflectorX, v);
        }
        if (reflect_y) {
            v = la.dot(twoD.reflectorY, v);
        }

        return dehomogenizeVector(v);
    }

    public Vector reflection3DX(Vector vector) {
        return reflection3D(vector, true, false, false);
    }

    public Vector reflection3DY(Vector vector) {
        return reflection3D(vector, false, true, false);
    }

    public Vector reflection3DZ(Vector vector) {
        return reflection3D(vector, false, false, true);
    }

    private Vector reflection3D(Vector vector, boolean reflectX, boolean reflectY, boolean reflectZ) {
        checkVector(vector, 3);

        Vector v = homogenizeVector(vector, 1);

        if (reflectX) {
            v = la.dot(threeD.reflectorX, v);
        }
        if (reflectY) {
            v = la.dot(threeD.reflectorY, v);
        }
        if (reflectZ) {
            v = la.dot(threeD.reflectorZ, v);
        }

        return dehomogenizeVector(v);
    }

    // ====================== Rotation ==========================

    public Vector rotation2D(Vector vector, double angdeg) {
        // accepts angle in degrees
        checkVector(vector, 2);

        Vector v = homogenizeVector(vector, 1);
        v = la.dot(twoD.rotator(angdeg), v);
        return dehomogenizeVector(v);
    }

    public Vector rotation3DX(Vector vector, double angdeg) {
        checkVector(vector, 3);

        Vector v = homogenizeVector(vector, 1);
        v = la.dot(threeD.rotatorX(angdeg), v);

        return dehomogenizeVector(v);
    }

    public Vector rotation3DY(Vector vector, double angdeg) {
        checkVector(vector, 3);

        Vector v = homogenizeVector(vector, 1);
        v = la.dot(threeD.rotatorY(angdeg), v);

        return dehomogenizeVector(v);
    }

    public Vector rotation3DZ(Vector vector, double angdeg) {
        checkVector(vector, 3);

        Vector v = homogenizeVector(vector, 1);
        v = la.dot(threeD.rotatorZ(angdeg), v);

        return dehomogenizeVector(v);
    }

    // ====================== Shearing ==========================

    public Vector shearing(Vector vector, double kx, double ky) {
        checkVector(vector, 2);

        Vector v = homogenizeVector(vector, 1);
        v = la.dot(twoD.shearer(kx, ky), v);

        return dehomogenizeVector(v);
    }

    // ===================== Helpers ==========================

    // method to create a vector in homogenous coordinates.
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

    // check is the vector is of dim dimension. if not, throw an error
    private void checkVector(Vector vector, int dim) {
        if (vector.dimension() != dim) {
            throw new IllegalArgumentException("Can only handle " + dim + " dimensional Vectors.");
        }
    }
}

class D2 {
    public D2() {}

    Matrix reflectorX = new Matrix(new double[][] {
    {1, 0, 0},
    {0, -1, 0},
    {0, 0, 1},
    });

    Matrix reflectorY = new Matrix(new double[][] {
    {-1, 0, 0},
    {0, 1, 0},
    {0, 0, 1},
    });

    Matrix rotator(double angdeg) {
        // angle in radians
        double r = Math.toRadians(angdeg);
        double sin = Math.sin(r);
        double cos = Math.cos(r);

        return new Matrix(new double[][] {
        {cos, -sin, 0},
        {sin,  cos, 0},
        {0,    0,   1},
        });

    }

        Matrix shearer(double kx, double ky) {
        return new Matrix(new double[][] {
        {1,  kx, 0},
        {ky, 1,  0},
        {0,  0,  1},
        });
    }
    

}

class D3 {
    D3() {}

    Matrix reflectorX = new Matrix(new double[][] {
    {1,  0,  0, 0},
    {0, -1,  0, 0},
    {0,  0, -1, 0},
    {0,  0,  0, 1},
    });

    Matrix reflectorY = new Matrix(new double[][] {
    {-1, 0,  0, 0},
    { 0, 1,  0, 0},
    { 0, 0, -1, 0},
    { 0, 0,  0, 1},
    });

    Matrix reflectorZ = new Matrix(new double[][] {
    {-1,  0, 0, 0},
    { 0, -1, 0, 0},
    { 0,  0, 1, 0},
    { 0,  0, 0, 1},
    });

    Matrix rotatorX(double angdeg) {
        double a = Math.toRadians(angdeg);

        double sin = Math.sin(a);
        double cos = Math.cos(a);

        return new Matrix(new double[][] {
        {1, 0,    0,   0},
        {0, cos, -sin, 0},
        {0, sin,  cos, 0},
        {0, 0,    0,   1},
        });
    }

    Matrix rotatorY(double angdeg) {
        double a = Math.toRadians(angdeg);

        double sin = Math.sin(a);
        double cos = Math.cos(a);

        return new Matrix(new double[][] {
        {cos,  0, sin, 0},
        {0,    1, 0,   0},
        {-sin, 0, cos, 0},
        {0,    0, 0,   1},
        });
    }

    Matrix rotatorZ(double angdeg) {
        double a = Math.toRadians(angdeg);

        double sin = Math.sin(a);
        double cos = Math.cos(a);

        return new Matrix(new double[][] {
        {cos, -sin, 0, 0},
        {sin,  cos, 0, 0},
        {0,    0,   1, 0},
        {0,    0,   0, 1},
        });
    }
}
