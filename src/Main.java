import runtime.Menu;
import matrix.*;
import pagerank.*;

public class Main {
    public static void main(String[] args) {
        Matrix a = new Matrix(new double[][]{
        {0,0,1,1},
        {1,0,0,0},
        {1,0,0,1},
        {1,1,1,0},
        });

        PageRank pg = new PageRank(a);

        int it = pg.calculate();
        System.out.println(pg.getHub());
        System.out.println(pg.getAuthority());

        System.out.println("Number of iterations = " + it);
    }

    public static void menu(String[] args) {
        Menu.main(args);
    }
}
