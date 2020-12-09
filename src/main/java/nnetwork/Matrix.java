package nnetwork;

/**
 * @author Jaroslaw Janas
 * @date 02/12/20
 * @project NN-Perceptron
 */

public class Matrix {
//    Class for matrices

    private final double[][] matrix;
    private int rows = 0;
    private int columns = 0;

//    init with an array
    public Matrix(double[][] matrix) {
        this.matrix = matrix;
        this.rows = matrix.length;
        this.columns = matrix[0].length;
    }

//    init with a rows and columns
    public Matrix(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        matrix = new double[rows][columns];
    }

//    dot product of two matrices
    public Matrix dotProduct(Matrix b) throws Exception {
        double[][] matrixB = b.getRaw();
        int bRows = matrixB.length, bColumns = matrixB[0].length;

        if (columns != bRows) {
            throw new Exception("Cannot multiply incompatible matrices "
                    + "[" + rows + " x " + columns + "][" + bRows + " x " + bColumns + "]");
        }

        double[][] output = new double[rows][bColumns];

//       https://www.programiz.com/java-programming/examples/multiply-matrix-function
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < bColumns; j++) {
                for (int k = 0; k < columns; k++) {
                    output[i][j] += matrix[i][k] * matrixB[k][j];
                }
            }
        }
//        -----------
        return new Matrix(output);
    }

//    hadmard product of two matrices
    public Matrix hadamardProduct(Matrix b) throws Exception {
        double[][] matrixB = b.getRaw();
        int bRows = matrixB.length, bColumns = matrixB[0].length;

        if (rows != bRows || columns != bColumns) {
            throw new Exception("Cannot apply hadamard product to matrices of different sizes "
                    + "[" + rows + " x " + columns + "][" + bRows + " x " + bColumns + "]");
        }

        double[][] output = new double[rows][columns];

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                output[r][c] = matrix[r][c] * matrixB[r][c];
            }
        }

        return new Matrix(output);
    }

//    raise each element to power of power
    public Matrix elementWisePower(double power) {
        double[][] output = new double[rows][columns];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                output[r][c] = Math.pow(matrix[r][c], power);
            }
        }

        return new Matrix(output);
    }

//    multiply by number b
    public Matrix multiply(double b) {
        double[][] output = new double[rows][columns];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                output[r][c] = matrix[r][c] * b;
            }
        }
        return new Matrix(output);
    }

//    add element wise number b
    public Matrix add(double b) throws Exception {
        Matrix matrixB = new Matrix(rows, columns);
        matrixB.initWith(b);
        return this.add(matrixB);
    }

//    add another matrix
    public Matrix add(Matrix b) throws Exception {
        double[][] matrixB = b.getRaw();
        int bRows = matrixB.length, bColumns = matrixB[0].length;

        if (rows != bRows || columns != bColumns) {
            throw new Exception("Cannot add incompatible matrices "
                    + "[" + rows + " x " + columns + "][" + bRows + " x " + bColumns + "]");
        }

        double[][] output = new double[rows][columns];

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                output[r][c] = matrix[r][c] + matrixB[r][c];
            }
        }

        return new Matrix(output);
    }

//    sum all elements in the matrix to a single value
    public double elementWiseSum() {
        double sum = 0;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                sum += matrix[r][c];
            }
        }

        return sum;
    }

//    subtract element wise using value b
    public Matrix subtract(double b) throws Exception {
        Matrix matrixB = new Matrix(rows, columns);
        matrixB.initWith(b);
        return this.subtract(matrixB);
    }

//    subtract using a matrix
    public Matrix subtract(Matrix b) throws Exception {
        Matrix negativeB = b.multiply(-1);
        return this.add(negativeB);
    }

//    flip
    public Matrix transpose() {
        double[][] output = new double[columns][rows];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                output[c][r] = matrix[r][c];
            }
        }
        return new Matrix(output);
    }

//    apply the sigmoid function element wise
    public Matrix sigmoid() {
        double[][] output = new double[rows][columns];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                output[r][c] = 1 / (1 + Math.pow(Math.E, -matrix[r][c]));
                ;
            }
        }
        return new Matrix(output);
    }

//    apply the sigmoid derivative function element wise
    public Matrix sigmoidDerivative() {
        double[][] output = new double[rows][columns];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                output[r][c] = Math.pow(Math.E, -matrix[r][c]) / Math.pow(1 + Math.pow(Math.E, -matrix[r][c]), 2);
            }
        }

        return new Matrix(output);
    }

//    init this matrix with value a
    public Matrix initWith(double a) {
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < columns; y++) {
                matrix[x][y] = a;
            }
        }
        return this;
    }

//    init with numbers - used for testing
    public Matrix initNumbers() {
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < columns; y++) {
                matrix[x][y] = (double) x + y + 1;
            }
        }
        return this;
    }

    public Matrix initRandom() {
        return initRandom(-1, 1);

    }

//    init in random range
    public Matrix initRandom(double min, double max) {
        double range = max - min;
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < columns; y++) {
                double rand = Math.random() * range + min;
                matrix[x][y] = rand;
            }
        }
        return this;
    }

//    get the position of the highest value
    public int[] highestValueIndex() {
        double highest = matrix[0][0];
        int[] position = {0, 0};

        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < columns; y++) {
                if (highest < matrix[x][y]) {
                    highest = matrix[x][y];
                    position[0] = x;
                    position[1] = y;
                }
            }
        }
        return position;
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        for (double[] row : matrix) {
            for (double val : row) {
                str.append(val).append(", ");
            }
            str.append("\n");
        }
        return str.toString();
    }

    public double[][] getRaw() {
        return matrix;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }
}
