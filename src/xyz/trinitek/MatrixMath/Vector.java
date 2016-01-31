package xyz.trinitek.MatrixMath;

public class Vector extends Matrix {

    /**
     * Create a vector, or a one-dimensional matrix. At least one dimension must be 1.
     * @param m    number of rows
     * @param n    number of columns
     * @throws MatrixSizeException if at least one of the dimensions is not 1.
     */
    public Vector(int m, int n) throws MatrixSizeException {
        super(m, n);
        if (!(m == 1 || n == 1)) {
            throw new MatrixSizeException("At least one dimension must be 1");
        }
    }

    /**
     * Create a vector, or a one-dimensional matrix, from an array of elements.
     * @param elements    vector elements
     */
    public Vector(double[] elements) {
        super(1, elements.length);
        this.elements[0] = elements;
    }

    /**
     * Get the dot product of the two vectors.
     * @param A    matrix A
     * @param B    matrix B
     * @return dot product
     * @throws MatrixSizeException if the lengths of A and B are not equal
     */
    public static double dotProduct(Vector A, Vector B) throws MatrixSizeException {
        Vector X, Y;
        if (A.getColumns() > B.getColumns()) {
            X = A;
            Y = B;
        } else {
            X = B;
            Y = A;
        }
        if (X.getColumns() != Y.getRows()) {
            throw new MatrixSizeException("Vector length mismatch");
        }
        double dot = 0;
        for (int i = 1; i <= X.getColumns(); i++) {
            dot += X.get(1, i) * Y.get(i, 1);
        }
        return dot;
    }



}
