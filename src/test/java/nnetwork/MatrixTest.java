package nnetwork;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Jaroslaw Janas
 * @date 02/12/20
 * @project NN-Perceptron
 */
public class MatrixTest {

    private final double[][] a = {
            {-5, 8, 0},
            {4, 2, -9},
            {2, 23, -4}
    };

    private final double[][] b = {
            {1, -13, 9, 1},
            {-3, 12, 0, 10},
            {22, 1, 3, 42}
    };

    private final double[][] c = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
    };

    private final double[][] d = {
            {1, 2},
            {3, 4}
    };

    private final double[][] e = {
            {1, -1, 2, -2}
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
        Matrix matrixA = new Matrix(a);
        Matrix matrixB = new Matrix(b);
        double[][] expected = {
                {-29, 161, -45, 75},
                {-200, -37, 9, -354},
                {-155, 246, 6, 64}
        };

        Matrix matrixProduct = null;
        try {
            matrixProduct = matrixA.dotProduct(matrixB);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertArrayEquals(expected, matrixProduct.getRaw());
        Assert.assertEquals(matrixA.getRows(), matrixProduct.getRows());
        Assert.assertEquals(matrixB.getColumns(), matrixProduct.getColumns());
    }

    @Test
    public void add() {
        Matrix matrixA = new Matrix(a);
        Matrix matrixC = new Matrix(c);
        double[][] expected = {
                {-4, 10, 3},
                {8, 7, -3},
                {9, 31, 5}
        };

        Matrix matrixSum = null;
        try {
            matrixSum = matrixA.add(matrixC);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertNotNull(matrixSum);
        Assert.assertArrayEquals(expected, matrixSum.getRaw());
    }

    @Test
    public void addScalar() {
        double addValue = 2.0;
        Matrix matrixC = new Matrix(c);
        double[][] expected = {
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
        Assert.assertArrayEquals(expected, matrixSum.getRaw());
    }

    @Test
    public void transpose() {
        double[][] expected = {
                {1, 4, 7},
                {2, 5, 8},
                {3, 6, 9}
        };
        Matrix matrixC = new Matrix(c);
        Matrix output = matrixC.transpose();

        Assert.assertArrayEquals(expected, output.getRaw());
    }

    @Test
    public void multiply() {
        double multiplyValue = -2;
        Matrix matrixA = new Matrix(a);
        double[][] expected = {
                {10, -16, -0.0},
                {-8, -4, 18},
                {-4, -46, 8}
        };
        Matrix output = matrixA.multiply(multiplyValue);

        Assert.assertArrayEquals(expected, output.getRaw());
    }

    @Test
    public void subtract() {
        Matrix matrixA = new Matrix(a);
        Matrix matrixC = new Matrix(c);
        double[][] expected = {
                {-6, 6, -3},
                {0, -3, -15},
                {-5, 15, -13}
        };

        Matrix output = null;
        try {
            output = matrixA.subtract(matrixC);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertNotNull(output);
        Assert.assertArrayEquals(expected, output.getRaw());
    }

    @Test
    public void subtractScalar() {
        double subtractValue = 10.0;
        Matrix matrixA = new Matrix(a);
        double[][] expected = {
                {-15, -2, -10},
                {-6, -8, -19},
                {-8, 13, -14}
        };

        Matrix output = null;
        try {
            output = matrixA.subtract(subtractValue);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertNotNull(output);
        Assert.assertArrayEquals(expected, output.getRaw());
    }

    @Test
    public void hadamardProduct() {
        Matrix matrixA = new Matrix(a);
        Matrix matrixB = new Matrix(c);
        double[][] expected = {
                {-5, 16, 0},
                {16, 10, -54},
                {14, 184, -36}
        };

        Matrix output1 = null;
        Matrix output2 = null;
        try {
            output1 = matrixA.hadamardProduct(matrixB);
            output2 = matrixB.hadamardProduct(matrixA);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertNotNull(output1);
        Assert.assertNotNull(output2);
        Assert.assertArrayEquals(expected, output1.getRaw());
        Assert.assertArrayEquals(output1.getRaw(), output2.getRaw());
    }

    @Test
    public void sigmoid() {
        Matrix matrixE = new Matrix(e);
        double[][] expected = {
                {0.7310585786300048792512, 0.2689414213699951207488, 0.8807970779778824440597, 0.1192029220221175559403}
        };

        Matrix output = matrixE.sigmoid();

        Assert.assertEquals(expected[0][0], output.getRaw()[0][0], 0.000000000000001);
        Assert.assertEquals(expected[0][1], output.getRaw()[0][1], 0.000000000000001);
        Assert.assertEquals(expected[0][2], output.getRaw()[0][2], 0.000000000000001);
        Assert.assertEquals(expected[0][3], output.getRaw()[0][3], 0.000000000000001);
    }

    @Test
    public void sigmoidDerivative() {
        Matrix matrixD = new Matrix(d);
        double[][] expected = {
                {0.1966119332414818525374, 0.1049935854035065173486},
                {0.04517665973091213264936, 0.01766270621329111642156}
        };

        Matrix output = matrixD.sigmoidDerivative();

        Assert.assertEquals(expected[0][0], output.getRaw()[0][0], 0.000000000000001);
        Assert.assertEquals(expected[1][1], output.getRaw()[1][1], 0.000000000000001);
    }
}