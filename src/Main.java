import matrix.Matrix;

public class Main {
    public static void main(String[] args) {

        Matrix A = new Matrix(new double[][] {
                {1,2,3},
                {0,1,4},
                {5,6,0},
            });
        System.out.println(A.determinant());
        System.out.println("program ran successfully ^_^");
    }
}
