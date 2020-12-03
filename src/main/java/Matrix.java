import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Jaroslaw Janas
 * @date 02/12/20
 * @project NN-Perceptron
 */

public class Matrix {
    private final double[][] matrix;
    private int rows = 0;
    private int columns = 0;

    public Matrix(double[][] matrix) {
        this.matrix = matrix;
        this.rows = matrix.length;
        this.columns = matrix[0].length;
    }

    public Matrix(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        matrix = new double[rows][columns];
    }

    public Matrix dotProduct(Matrix b) throws Exception {
        double[][] matrixB = b.getRaw();
        int bRows = matrixB.length, bColumns = matrixB[0].length;

        if(columns != bRows){
            throw new Exception("Cannot multiply incompatible matrices");
        }

        double[][] output = new double[rows][bColumns];

//       https://www.programiz.com/java-programming/examples/multiply-matrix-function
        for(int i = 0; i < rows; i++) {
            for (int j = 0; j < bColumns; j++) {
                for (int k = 0; k < columns; k++) {
                    output[i][j] += matrix[i][k] * matrixB[k][j];
                }
            }
        }
//        -----------
        return new Matrix(output);
    }

    public Matrix add(Matrix b) throws Exception {
        double[][] matrixB = b.getRaw();
        int bRows = matrixB.length, bColumns = matrixB[0].length;

        if(rows != bRows || columns != bColumns){
            throw new Exception("Cannot add incompatible matrices");
        }

        double[][] output = new double[rows][columns];

        for(int r = 0; r<rows; r++){
            for(int c = 0; c<columns; c++){
                output[r][c] = matrix[r][c] + matrixB[r][c];
            }
        }

        return new Matrix(output);
    }

    public void initNumbers(){
        for(int x=0; x<rows; x++){
            for(int y=0; y<columns; y++){
                matrix[x][y] = (double) x+y+1;
            }
        }
    }

    public void initRandom(){
        initRandom(-1, 1);
    }

    public void initRandom(double min, double max){
        double range =  max-min;
        for(int x=0; x<rows; x++){
            for(int y=0; y<columns; y++){
                double rand = Math.random() * range + min;
                matrix[x][y] = rand;
            }
        }
    }

    public void print(){
        for(double[] row : matrix){
            for(double val : row){
                System.out.print(val + ", ");
            }
            System.out.println();
        }
    }

    public double[][] getRaw(){
        return matrix;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }
}
