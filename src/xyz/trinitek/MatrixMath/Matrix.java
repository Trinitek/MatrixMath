package xyz.trinitek.MatrixMath;

public class Matrix {

    protected double[][] elements;

    /**
     * Create a zero matrix.
     * @param m    number of rows
     * @param n    number of columns
     * @throws NegativeArraySizeException if the dimensions are invalid
     */
    public Matrix(int m, int n) throws NegativeArraySizeException {
        this.elements = new double[m][n];
    }

    /**
     * Create a matrix from the given 2D matrix.
     * @param elements    matrix elements
     * @throws NullPointerException if the element array is null
     * @throws MatrixSizeException if the row sizes are inconsistent
     */
    public Matrix(double[][] elements) throws NullPointerException, MatrixSizeException {
        if (elements == null) {
            throw new NullPointerException("Element array is null");
        }
        int prev = elements[0].length;
        for (double[] row : elements) {
            if (prev != row.length) {
                throw new MatrixSizeException("Row sizes are inconsistent");
            }
            prev = row.length;
        }
        this.elements = elements;
    }

    /**
     * Get the number of rows in the matrix, or the height.
     * @return rows
     */
    public int getRows() {
        return this.elements.length;
    }

    /**
     * Get the number of columns in the matrix, or the width.
     * @return columns
     */
    public int getColumns() {
        if (this.elements.length == 0) {
            return 0;
        } else {
            return this.elements[0].length;
        }
    }

    /**
     * Get the element in the matrix at m*n.
     * @param m    row selector
     * @param n    column selector
     * @return element
     * @throws IndexOutOfBoundsException
     */
    public double get(int m, int n) throws IndexOutOfBoundsException {
        return this.elements[--m][--n];
    }

    /**
     * Set the element in the matrix at m*n to a.
     * @param m    row selector
     * @param n    column selector
     * @param a    new element
     * @throws IndexOutOfBoundsException
     */
    public void set(int m, int n, double a) throws IndexOutOfBoundsException {
        this.elements[--m][--n] = a;
    }

    /**
     * Get the transposed matrix of this matrix. The original is unmodified.
     * @return transposed matrix
     */
    public Matrix transpose() {
        Matrix transposed = new Matrix(this.getColumns(), this.getRows());
        if (this instanceof Vector) {
            try {
                transposed = new Vector(this.getColumns(), this.getRows());
            } catch (MatrixSizeException e) {
                e.printStackTrace(System.err);
            }
        }
        for (int m = this.getRows(); m > 0; m--) {
            for (int n = this.getColumns(); n > 0; n--) {
                transposed.set(n, m, this.get(m, n));
            }
        }
        return transposed;
    }

    /**
     * Create a square scalar matrix.
     * @param m    number of columns and rows
     * @param c
     * @return a new scalar matrix
     */
    public static Matrix makeScalar(int m, double c) {
        Matrix matrix = new Matrix(m, m);
        for (; m > 0; m--) {
            matrix.set(m, m, c);
        }
        return matrix;
    }

    /**
     * Create a square identity matrix.
     * @param m    number of columns and rows
     * @return a new identity matrix
     */
    public static Matrix makeIdentity(int m) {
        return makeScalar(m, 1);
    }

    /**
     * Add matrix B to matrix A.
     * @param A    matrix A
     * @param B    matrix B
     * @return new matrix equal to (A + B)
     * @throws MatrixSizeException if the two matrices are not the same dimensions
     */
    public static Matrix add(Matrix A, Matrix B) throws MatrixSizeException {
        if (A.getRows() != B.getRows() || A.getColumns() != B.getColumns()) {
            throw new MatrixSizeException("Matrices must be the same dimensions");
        }
        Matrix X = A.clone();
        for (int m = 1; m <= A.getRows(); m++) {
            for (int n = 1; n <= A.getColumns(); n++) {
                X.set(m, n, A.get(m, n) + B.get(m, n));
            }
        }
        return X;
    }

    /**
     * Subtract matrix B from matrix A.
     * @param A    matrix A
     * @param B    matrix B
     * @return new matrix equal to (A - B)
     * @throws MatrixSizeException if the two matrices are not the same dimensions
     */
    public static Matrix sub(Matrix A, Matrix B) throws MatrixSizeException {
        return add(A, mul(B.clone(), -1));
    }

    /**
     * Multiply matrix A by scalar c.
     * @param A    matrix A
     * @param c    scalar multiplier
     * @return product matrix
     */
    public static Matrix mul(Matrix A, double c) {
        Matrix X = A.clone();
        for (int m = 1; m <= A.getRows(); m++) {
            for (int n = 1; n <= A.getColumns(); n++) {
                X.set(m, n, A.get(m, n) * c);
            }
        }
        return X;
    }

    /**
     * Multiply matrix A by matrix B.
     * @param A    matrix A
     * @param B    matrix B
     * @return product matrix
     * @throws MatrixSizeException if the height of matrix A is not equal to the width of matrix B
     */
    public static Matrix mul(Matrix A, Matrix B) throws MatrixSizeException {
        if (A.getRows() != B.getColumns()) {
            throw new MatrixSizeException("Matrix A's height must be equal to matrix B's width");
        }
        Matrix X = new Matrix(A.getColumns(), B.getRows());
        for (int m = 1; m <= X.getRows(); m++) {
            for (int n = 1; n <= X.getColumns(); n++) {
                X.set(m, n, Vector.dotProduct(A.getVectorFromRow(m), B.getVectorFromColumn(n)));
            }
        }
        return X;
    }

    /**
     * Divide matrix A by scalar c.
     * @param A    destination matrix
     * @param c    scalar multiplier
     * @return matrix A
     */
    public static Matrix div(Matrix A, double c) {
        return mul(A, 1/c);
    }

    /**
     * Get a copy of this matrix as a 2D array of doubles.
     * @return 2D array
     */
    public double[][] asArray() {
        return this.clone().elements;
    }

    /**
     * Swap two rows in the matrix.
     * @param m1    primary row selector
     * @param m2    secondary row selector
     * @throws ArrayIndexOutOfBoundsException if one or both of the selectors are out of range
     */
    public void swapRow(int m1, int m2) throws ArrayIndexOutOfBoundsException {
        double[] temp = this.elements[--m1];
        this.elements[m1] = this.elements[--m2];
        this.elements[m2] = temp;
    }

    /**
     * Swap two columns in the matrix.
     * @param n1    primary column selector
     * @param n2    secondary column selector
     * @throws ArrayIndexOutOfBoundsException if one or both of the selectors are out of range
     */
    public void swapColumn(int n1, int n2) throws ArrayIndexOutOfBoundsException {
        Matrix temp = this.transpose();
        temp.swapRow(n1, n2);
        temp = temp.transpose();
        this.elements = temp.elements;
    }

    /**
     * Extract a vector from the specified column.
     * @param n    column selector
     * @return new vector
     * @throws ArrayIndexOutOfBoundsException if the column selector is out of range
     */
    public Vector getVectorFromColumn(int n) throws ArrayIndexOutOfBoundsException {
        return (Vector) new Vector(this.transpose().elements[--n]).transpose();
    }

    /**
     * Extract a vector from the specified row.
     * @param m    row selector
     * @return new vector
     * @throws ArrayIndexOutOfBoundsException if the row selector is out of range
     */
    public Vector getVectorFromRow(int m) throws ArrayIndexOutOfBoundsException {
        return new Vector(this.elements[--m]);
    }

    /**
     * Create a duplicate but separate matrix.
     * @return new matrix
     */
    @SuppressWarnings("CloneDoesntCallSuperClone")
    @Override
    public Matrix clone() {
        double[][] elements = new double[this.getRows()][this.getColumns()];
        for (int m = 0; m < this.getRows(); m++) {
            System.arraycopy(this.elements[m], 0, elements[m], 0, this.getColumns());
        }
        try {
            return new Matrix(elements);
        } catch (MatrixSizeException e) {
            e.printStackTrace(System.err);
            return null;
        }
    }

    /**
     * Get the string representation of this matrix.
     * @return string representation
     */
    @Override
    public String toString() {
        String string = "(" + this.getRows() + ", " + this.getColumns() + ") {\n";
        for (int m = 1; m <= this.getRows(); m++) {
            string = string.concat("[");
            for (int n = 1; n <= this.getColumns(); n++) {
                string = string.concat(" " + this.get(m, n) + " ");
            }
            string = string.concat("]\n");
        }
        string = string.concat("}");
        return string;
    }

}
