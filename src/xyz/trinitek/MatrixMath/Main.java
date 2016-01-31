package xyz.trinitek.MatrixMath;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello world!");

        Matrix matrix = new Matrix(0, 0);
        System.out.println(matrix.toString());

        Matrix m1 = Matrix.makeScalar(3, 4);
        System.out.println(m1.toString());

        Matrix m2 = Matrix.makeIdentity(4);
        System.out.println(m2.toString());

        Matrix m3 = m1.clone();

        m1.set(1, 2, 9);

        System.out.println(m3.toString());

        System.out.println(m1.toString());

        try {
            System.out.println(Matrix.add(m3, m1));
        } catch (MatrixSizeException e) {
            e.printStackTrace(System.err);
        }

        double[][] data1 = {{1, 2, 3}, {4, 5, 6}};
        Matrix m4;
        try {
            m4 = new Matrix(data1);
        } catch (MatrixSizeException e) {
            e.printStackTrace(System.err);
            return;
        }
        System.out.println(m4.toString());

        Matrix m5 = m4.transpose();
        System.out.println(m5.toString());

        m5 = Matrix.mul(m5, 3);
        System.out.println(m5);

        Vector v1 = m5.getVectorFromColumn(1);
        Vector v2 = (Vector) m5.getVectorFromColumn(2).transpose();

        System.out.println(v1.toString());
        System.out.println(v2.toString());

        double dot1;
        try {
            dot1 = Vector.dotProduct(v1, v2);
        } catch (MatrixSizeException e) {
            e.printStackTrace(System.err);
            return;
        }
        System.out.println(dot1);

        Matrix m6;
        try {
            m6 = new Matrix(new double[][]{{1, 2}, {3, 4}});
        } catch (MatrixSizeException e) {
            e.printStackTrace(System.err);
            return;
        }
        System.out.println(m6);

        Matrix m7 = Matrix.makeScalar(2, 2);
        System.out.println(m7);

        Matrix m8;
        try {
            m8 = Matrix.mul(m6, m7);
        } catch (MatrixSizeException e) {
            e.printStackTrace(System.err);
            return;
        }
        System.out.println(m8);

        m8.swapRow(1, 2);
        System.out.println(m8);

        m8.swapColumn(1, 2);
        System.out.println(m8);
    }

}
