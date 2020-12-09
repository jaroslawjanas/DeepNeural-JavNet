import ndata.DataFile;
import nnetwork.LayerManager;
import nnetwork.Matrix;
import nnormal.Normalizer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Jaroslaw Janas
 * @date 02/12/20
 * @project NN-Perceptron
 */
public class Main {

    //    https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
//    ---------

    public static void main(String[] args) {
        String filePath = "temp/shuffledTrain.txt";
        String delimiter = "\t";
        int classColumn = 3;
        int inputSize = 9;
        int hiddenLayerCount = 1;
        int layerSize = 16;
        int outputSize = 3;


        DataFile trainFile = new DataFile(filePath, delimiter, 3);
        ArrayList<String> trainingClasses = trainFile.getClasses();
        ArrayList<ArrayList<Double>> trainingData = trainFile.getData();

        //normalizes data in a certain range with respect to the data in each column
        Normalizer normalizer = new Normalizer(trainingData, -1, 1);
        trainingData = normalizer.normalize(trainingData);

        DataFile testFile = new DataFile(filePath, delimiter, classColumn);
        ArrayList<String> testingClasses = testFile.getClasses();
        ArrayList<ArrayList<Double>> testingData = testFile.getData();
//        note that the same normalizer is used to normalize the testingData
//        with the same parameters and with respect to the trainingData
        testingData = normalizer.normalize(testingData);


//        get unique classes and sort them, their order is the output node they belong to
        Set<String> set = new HashSet<>(testingClasses);
        ArrayList<String> uniqueClasses = new ArrayList<>();
        uniqueClasses.addAll(set);
//        https://stackoverflow.com/questions/5815423/sorting-arraylist-in-alphabetical-order-case-insensitive
        uniqueClasses.sort(String::compareToIgnoreCase);
//        ------
        outputSize = uniqueClasses.size();
        System.out.println("Detected unique classes: " + uniqueClasses);


//        init the neural network
        LayerManager layerManager = null;
        try {
            layerManager = new LayerManager(inputSize, hiddenLayerCount, layerSize, outputSize);
            layerManager.initAllRandom();
        } catch (Exception e) {
            e.printStackTrace();
        }


//        training
        for (int row = 0; row < trainingData.get(0).size(); row++) {

            double[][] rowData = new double[trainingData.size()][1];
            for (int col = 0; col < trainingData.size(); col++) {
                rowData[col][0] = trainingData.get(col).get(row);
            }

            Matrix input = new Matrix(rowData);
            try {
                assert layerManager != null;
                Matrix predicted = layerManager.feedForward(input);

//                get position of the highest value from predicted {row, column}
                int[] position = predicted.highestValueIndex();

//                check accuracy
                String predictedClass = uniqueClasses.get(position[0]);
                String expectedClass = trainingClasses.get(row);
                if (predictedClass.equalsIgnoreCase(expectedClass)) {
                    System.out.print(ANSI_GREEN);
                } else {
                    System.out.print(ANSI_RED);
                }
                System.out.println("Predicted: " + predictedClass + "  " + "Expected: " + expectedClass);
                System.out.print(ANSI_RESET);

//                construct expected Matrix
                Matrix expected = new Matrix(outputSize, 1);
                double[][] expectedArray = expected.initWith(0).getRaw();
//                get position of the expected class in the uniqueClasses
                int expectedIndex = uniqueClasses.indexOf(expectedClass);
                expectedArray[expectedIndex][0] = 1;
                expected = new Matrix(expectedArray);

//                back propagate
                layerManager.backPropagate(predicted, expected, 3);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
