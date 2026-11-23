package pagerank;
import matrix.*;

public class PageRank {
    public Vector center;
    public Vector authority;

    public PageRank(Matrix adjacencyMatrix) {
        Matrix m = adjacencyMatrix;
        if (!m.isSquare()) {
            throw new IllegalArgumentException("Adjacency matrix must be square.");
        }

        Vector center = new Vector(m.getColumns());
        Vector authority = new Vector(m.getRows());

        for (int i = 1; i <= m.getColumns(); i++) {
            center.set(i, m.getRowSum(i));
        }

        for (int i = 1; i <= m.getRows(); i++) {
            authority.set(i, m.getColumnSum(i));
        }

        this.center = center;
        this.authority = authority;
    }
}
