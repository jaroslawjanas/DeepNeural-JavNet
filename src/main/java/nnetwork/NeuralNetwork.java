package nnetwork;

import ndata.DataExporter;
import ndata.DataFile;
import nnormal.Normalizer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
* @author Jaroslaw Janas
* @date 09/12/20
* @project NN-Perceptron
 *
*/public class NeuralNetwork {
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

    private String delimiter = "\t";
    private double learningRate = 1;
    private int classColumn = 3;
    private int hiddenLayerCount = 1;
    private int layerSize = 16;
    private boolean initWith = false;
    private double initWithValue = 0.0;
    private boolean initRand = true;
    private double initRandMin = -1.0;
    private double initRandMax = -1.0;
    private boolean detailedOutput=false;

    public NeuralNetwork(){
//        settings
        try {
            File settingsFile = new File("settings.properties");
            InputStream inputStream = new FileInputStream(settingsFile);
            Properties settings = new Properties();
            settings.load(inputStream);

            delimiter = settings.getProperty("delimiter").replaceAll("\"", "");
            learningRate = Double.parseDouble(settings.getProperty("learningRate"));
            classColumn = Integer.parseInt(settings.getProperty("classColumn"));
            hiddenLayerCount = Integer.parseInt(settings.getProperty("hiddenLayerCount"));
            layerSize = Integer.parseInt(settings.getProperty("layerSize"));
            initWith = Boolean.parseBoolean(settings.getProperty("initWith"));
            initWithValue = Double.parseDouble(settings.getProperty("initWithValue"));
            initRand = Boolean.parseBoolean(settings.getProperty("initRand"));
            initRandMin = Double.parseDouble(settings.getProperty("initRandMin"));
            initRandMax = Double.parseDouble(settings.getProperty("initRandMax"));
            detailedOutput = Boolean.parseBoolean(settings.getProperty("detailedOutput"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void evaluateNN(String trainFilePath, String testFilePath){
        DataFile trainFile = new DataFile(trainFilePath, delimiter, 3);
        ArrayList<String> trainingClasses = trainFile.getClasses();
        ArrayList<ArrayList<Double>> trainingData = trainFile.getData();

        //normalizes data in a certain range with respect to the data in each column
        Normalizer normalizer = new Normalizer(trainingData, -1, 1);
        trainingData = normalizer.normalize(trainingData);

        DataFile testFile = new DataFile(testFilePath, delimiter, classColumn);
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
        int outputSize = uniqueClasses.size();
        System.out.println("Detected unique classes: " + uniqueClasses);


//        init the neural network
        LayerManager layerManager = null;
        try {
            layerManager = new LayerManager(trainingData.size(), hiddenLayerCount, layerSize, outputSize);

            if (initWith) {
                layerManager.initAllWith(initWithValue);
            } else if (initRand) {
                layerManager.initAllRandom(initRandMin, initRandMax);
            } else {
                throw new Exception("At least one initialization method must be chosen");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


//        training
        StringBuilder trainingOutput = new StringBuilder();
        System.out.println(ANSI_YELLOW + "Training using " + trainFilePath + ANSI_RESET);
        StringBuilder detailedOutputLog = new StringBuilder();
        for (int row = 0; row < trainingData.get(0).size(); row++) {

//            construct a row matrix
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
                    detailedOutputLog.append(ANSI_GREEN);
                } else {
                    detailedOutputLog.append(ANSI_RED);
                }

                detailedOutputLog.append("Predicted: ").append(predictedClass)
                        .append("  Expected: ").append(expectedClass).append(ANSI_RESET).append("\n");

//                construct expected Matrix
                Matrix expected = new Matrix(outputSize, 1);
                double[][] expectedArray = expected.initWith(0).getRaw();
//                get position of the expected class in the uniqueClasses
                int expectedIndex = uniqueClasses.indexOf(expectedClass);
                expectedArray[expectedIndex][0] = 1;
                expected = new Matrix(expectedArray);

//                cost
                double cost = predicted.subtract(expected).elementWisePower(2.0).elementWiseSum();
                detailedOutputLog.append("\t\t\t\t\t\t")
                        .append(ANSI_YELLOW).append("Cost: ").append(cost).append(ANSI_RESET).append("\n");

//                back propagate
                layerManager.backPropagate(predicted, expected, learningRate);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(detailedOutput){
            System.out.print(detailedOutputLog.toString());
        }

//        save training output prediction to a file
        DataExporter trainOutputFile = new DataExporter("data/output", "trainOutput.txt");
        trainOutputFile.write(trainingOutput.toString());

//      ------------------------------------------------------------------------------------------

//        testing
        int correct = 0, wrong = 0;
        System.out.println("\n" + ANSI_YELLOW + "Testing using " + testFilePath + ANSI_RESET);
        detailedOutputLog = new StringBuilder();
        for (int row = 0; row < testingData.get(0).size(); row++) {

            double[][] rowData = new double[testingData.size()][1];
            for (int col = 0; col < testingData.size(); col++) {
                rowData[col][0] = testingData.get(col).get(row);
            }

            Matrix input = new Matrix(rowData);
            try {
                assert layerManager != null;
                Matrix predicted = layerManager.feedForward(input);

//                get position of the highest value from predicted {row, column}
                int[] position = predicted.highestValueIndex();

//                check accuracy
                String predictedClass = uniqueClasses.get(position[0]);
                String expectedClass = testingClasses.get(row);
                if (predictedClass.equalsIgnoreCase(expectedClass)) {
                    correct++;
                    detailedOutputLog.append(ANSI_GREEN);
                } else {
                    wrong++;
                    detailedOutputLog.append(ANSI_RED);
                }
                detailedOutputLog.append("Predicted: ").append(predictedClass)
                        .append("  Expected: ").append(expectedClass).append(ANSI_RESET).append("\n");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

//        save testing output prediction to a file
        DataExporter testOutputFile = new DataExporter("data/output", "testOutput.txt");

        if(detailedOutput){
            System.out.print(detailedOutputLog.toString());
        }

//        stats
        System.out.println("\n" + ANSI_YELLOW + "Statistics" + ANSI_RESET);
        System.out.println(ANSI_WHITE + "Correct: " + ANSI_GREEN + correct + ANSI_RESET);
        System.out.println(ANSI_WHITE + "Wrong: " + ANSI_RED + wrong + ANSI_RESET);
        double accuracy = (double) correct / (correct + wrong) * 100;
        System.out.println(ANSI_WHITE + "Accuracy: " + ANSI_YELLOW + accuracy + " %" + ANSI_RESET);
    }
}
