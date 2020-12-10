import ndata.DataFile;
import ndata.DataFileSplitter;
import nnetwork.nnManager;

import java.io.*;
import java.util.ArrayList;
import java.util.Properties;

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
//        settings
        String trainFilePath = "data/train.txt";
        String testFilePath = "data/test.txt";
        boolean singleFileTests = false;
        String singleFilePath = "data/full.txt";
        int singleFileTestIterations = 10;

        int classColumn = 3;
        String delimiter = "\t";


//        settings import
        try {
            File settingsFile = new File("settings.properties");
            InputStream inputStream = new FileInputStream(settingsFile);
            Properties settings = new Properties();
            settings.load(inputStream);

            trainFilePath = settings.getProperty("trainFilePath");
            testFilePath = settings.getProperty("testFilePath");
            singleFileTests = Boolean.parseBoolean(settings.getProperty("singleFileTests"));
            singleFilePath = settings.getProperty("singleFilePath");
            classColumn = Integer.parseInt(settings.getProperty("classColumn"));
            delimiter = settings.getProperty("delimiter").replaceAll("\"", "");
            singleFileTestIterations = Integer.parseInt(settings.getProperty("singleFileTestIterations"));

        } catch (IOException e) {
            e.printStackTrace();
        }


        ArrayList<ArrayList<Double>> trainingData;
        ArrayList<String> trainingClasses;
        ArrayList<ArrayList<Double>> testingData;
        ArrayList<String> testingClasses;

        nnManager nn = new nnManager();
        if(!singleFileTests){
            DataFile trainFile = new DataFile(trainFilePath, delimiter, 3);
            trainingClasses = trainFile.getClasses();
            trainingData = trainFile.getData();

            DataFile testFile = new DataFile(testFilePath, delimiter, classColumn);
            testingClasses = testFile.getClasses();
            testingData = testFile.getData();

            nn.evaluateNN(trainingData, trainingClasses, testingData, testingClasses, "");
        }
        else {
            double totalAccuracy = 0;
            for(int i = 0; i<singleFileTestIterations; i++) {
                DataFileSplitter sFile = new DataFileSplitter(singleFilePath, delimiter, classColumn);

                trainingData = sFile.getTrainingData();
                trainingClasses = sFile.getTrainingClasses();
                testingData = sFile.getTestingData();
                testingClasses = sFile.getTestingClasses();


                nn.evaluateNN(trainingData, trainingClasses, testingData, testingClasses, (i+1) + "_");
                totalAccuracy += nn.getAccuracy();
            }

            double averageAccuracy = totalAccuracy/singleFileTestIterations;
            System.out.println("\n\nTotal Accuracy: " + averageAccuracy + " %");
        }


    }
}
