import matrix.Matrix;

public class Main {
    public static void main(String[] args) {

        Matrix A = new Matrix(new double[][] {
                {0.630,0.021,4.581},
                {0.019,0.136,3.647},
            });
        Matrix B = new Matrix(new double[][] {
                {0.365,4.469},
                {8.417,1.754},
                {1.104,0.801},
            });

        Matrix D = A.multiply(B);
        Matrix E = B.multiply(A);
        System.out.println(D);
        System.out.println(E);
        System.out.println("program ran successfully ^_^");
    }
}
