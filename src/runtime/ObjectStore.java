package runtime;
import matrix.Matrix;
import matrix.Vector;

import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

public class ObjectStore {
    private final Map<String, Vector> vectors = new HashMap<>();
    private final Map<String, Matrix> matrices = new HashMap<>();

    public void add(String name, Vector vector) {
        if (vectors.containsKey(name)) {
            throw new IllegalArgumentException(
                "An object with that name already exists."
            );
        }

        vectors.put(name, vector);
    }

    public void add(String name, Matrix matrix) {
        if (matrices.containsKey(name)) {
            throw new IllegalArgumentException(
                "An object with that name already exists."
            );
        }

        matrices.put(name, matrix);
    }

    public void replaceVector(String name, Vector vector) {
        if (!vectors.containsKey(name)) {
            throw new IllegalArgumentException(
                "No vector with that name exists."
            );
        }

        vectors.put(name, vector);
    }

    public Vector getVector(String name) {
        return vectors.get(name);
    }

    public Matrix getMatrix(String name) {
        return matrices.get(name);
    }

    public Map<String, Vector> getVectors() {
        return Collections.unmodifiableMap(vectors);
    }
}
