package nnetwork;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Jaroslaw Janas
 * @date 02/12/20
 * @project NN-Perceptron
 */
public class MatrixTest {

    private double[][] a = {
            {-5, 8, 0},
            {4, 2, -9},
            {2, 23, -4}
    };

    private double[][] b = {
            {1, -13, 9, 1},
            {-3, 12, 0, 10},
            {22, 1, 3, 42}
    };

    private double[][] c = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
    };

    @Test
    public void newMatrixWithArray() {
        Matrix m = new Matrix(a);
        Assert.assertArrayEquals(a, m.getRaw());
        Assert.assertEquals(a.length, m.getRows());
        Assert.assertEquals(a[0].length, m.getColumns());
    }

    @Test
    public void initNumbers() {
        Matrix m = new Matrix(10, 1);
        m.initNumbers();

        double[][] mArr = m.getRaw();

        Assert.assertEquals(10, mArr.length);
        Assert.assertEquals(1, mArr[0].length);
        Assert.assertEquals(10, mArr[9][0], 0.0001);
    }

    @Test
    public void dotProduct() {
        double[][] product = {
                {-29, 161, -45, 75},
                {-200, -37, 9, -354},
                {-155, 246, 6, 64}
        };
        Matrix matrixA = new Matrix(a);
        Matrix matrixB = new Matrix(b);

        Matrix matrixProduct = null;
        try {
            matrixProduct = matrixA.dotProduct(matrixB);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertArrayEquals(product, matrixProduct.getRaw());
        Assert.assertEquals(matrixA.getRows(), matrixProduct.getRows());
        Assert.assertEquals(matrixB.getColumns(), matrixProduct.getColumns());
    }

    @Test
    public void add() {
        double[][] sum = {
                {-4, 10, 3},
                {8, 7, -3},
                {9, 31, 5}
        };

        Matrix matrixA = new Matrix(a);
        Matrix matrixC = new Matrix(c);

        Matrix matrixSum = null;
        try {
            matrixSum = matrixA.add(matrixC);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertNotNull(matrixSum);
        Assert.assertArrayEquals(sum, matrixSum.getRaw());
    }

    @Test
    public void addScalar() {
        double addValue = 2.0;
        Matrix matrixC = new Matrix(c);
        double[][] addedC = {
                {3, 4, 5},
                {6, 7, 8},
                {9, 10, 11}
        };
        Matrix matrixSum = null;
        try {
            matrixSum = matrixC.add(addValue);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertNotNull(matrixSum);
        Assert.assertArrayEquals(addedC, matrixSum.getRaw());
    }

    @Test
    public void transpose() {
        double[][] transposedC = {
                {1, 4, 7},
                {2, 5, 8},
                {3, 6, 9}
        };
        Matrix matrixC = new Matrix(c);
        Matrix output = matrixC.transpose();

        Assert.assertArrayEquals(transposedC, output.getRaw());
    }

    @Test
    public void multiply() {
        double multiplyValue = -2;
        Matrix matrixA = new Matrix(a);
        double[][] multipliedA = {
                {10, -16, -0.0},
                {-8, -4, 18},
                {-4, -46, 8}
        };
        Matrix output = matrixA.multiply(multiplyValue);

        Assert.assertArrayEquals(multipliedA, output.getRaw());
    }
}
