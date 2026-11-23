import runtime.Menu;
import matrix.*;
import pagerank.PageRank;

public class Main {
    public static void main(String[] args) {
        Matrix a = new Matrix(new double[][]{
        {0,0,1,1},
        {1,0,0,0},
        {1,0,0,1},
        {1,1,1,0},
        });

        PageRank pg = new PageRank(a);

        System.out.println(pg.center);
        System.out.println(pg.authority);
    }

    public static void menu(String[] args) {
        Menu.main(args);
    }
}
