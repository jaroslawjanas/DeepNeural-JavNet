import ndata.DataExporter;
import ndata.DataFile;
import nnetwork.LayerManager;
import nnetwork.Matrix;
import nnetwork.NeuralNetwork;
import nnormal.Normalizer;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Properties;
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
        String trainFilePath = "data/train.txt";
        String testFilePath = "data/test.txt";
        boolean singleFileTests = false;
        String singleFile = "data/full.txt";

//        settings
        try {
            File settingsFile = new File("settings.properties");
            InputStream inputStream = new FileInputStream(settingsFile);
            Properties settings = new Properties();
            settings.load(inputStream);

            trainFilePath = settings.getProperty("trainFilePath");
            testFilePath = settings.getProperty("testFilePath");
            singleFileTests = Boolean.parseBoolean(settings.getProperty("singleFileTests"));
            singleFile = settings.getProperty("singleFile");

        } catch (IOException e) {
            e.printStackTrace();
        }

        NeuralNetwork nn = new NeuralNetwork();

        if(!singleFileTests){
            nn.evaluateNN(trainFilePath, testFilePath);
        }


    }
}
