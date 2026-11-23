package pagerank;
import matrix.*;

public class PageRank {
    private Matrix adjacencyMatrix;
    private Vector hub;
    private Vector authority;
    private static final double ERR = 1e-10;
    private static final int MAX_ITERATIONS = 1000;
    private static final LinearAlgebra la = new LinearAlgebra();

    public PageRank(Matrix adjacencyMatrix) {
        Matrix m = adjacencyMatrix;
        if (!m.isSquare()) {
            throw new IllegalArgumentException("Adjacency matrix must be square.");
        }

        for (int i = 1; i <= m.getRows(); i++) {
            for (int j = 1; j <= m.getColumns(); j++) {
                if (m.get(i, j) != 1 && m.get(i, j) != 0) {
                    throw new IllegalArgumentException("Elements of adjacency matrix must be only 1 or 0.");
                }
            }
        }

        this.adjacencyMatrix = m;

        Vector hub = new Vector(m.getColumns());
        Vector authority = new Vector(m.getRows());

        for (int i = 1; i <= m.getColumns(); i++) {
            hub.set(i, m.getRowSum(i));
        }

        for (int i = 1; i <= m.getRows(); i++) {
            authority.set(i, m.getColumnSum(i));
        }

        this.hub = hub;
        this.authority = authority;
    }

    public Vector getHub() { return new Vector(this.hub); }

    public Vector getAuthority() { return new Vector(this.authority); }

    public int calculate() {
        // calculates the hub and authority until either the difference between them is <ERR
        // or it has reached the maximum number of iterations.
        Vector prevHub;
        Vector prevAuthority;
        double hubDiff = 999999999.99999999999999;
        double authorityDiff = hubDiff;
        int it = 0;

        while ((hubDiff > ERR || authorityDiff > ERR) && it < MAX_ITERATIONS) {
            prevHub = new Vector(this.hub);
            prevAuthority = new Vector(this.authority);
            calculate(1);

            hubDiff = difference(this.hub, prevHub);
            authorityDiff = difference(this.authority, prevAuthority);

            it++;
        }

        // returns the number of iterations performed
        return it;
    }

    public void calculate(int iterations) {
        // calculates the next hub and authority 'iterations' number of times
        Vector nextHub;
        Vector nextAuthority;

        for (int i = 0; i < iterations; i++) {
            nextHub = nextHub(this.authority);
            this.hub = nextHub;

            nextAuthority = nextAuthority(this.hub);
            this.authority = nextAuthority;
        }
    }

    private Vector nextAuthority(Vector hub) {
        Vector numerator = la.dot(la.transpose(adjacencyMatrix), hub);
        double denominator = numerator.getNorm();
        checkDenominator(denominator);

        return la.times(1/denominator, numerator);
    }

    private Vector nextHub(Vector authority) {
        Vector numerator = la.dot(adjacencyMatrix, authority);
        double denominator = numerator.getNorm();
        checkDenominator(denominator);

        return la.times(1/denominator, numerator);
    }

    private double difference(Vector curr, Vector prev) {
        return curr.subtract(prev).getNorm();
    }

    private void checkDenominator(double denominator) {
        if (denominator == 0) {
            throw new IllegalStateException("Denominator is 0. cannot normalize a zero vector.");
        }
    }
}

