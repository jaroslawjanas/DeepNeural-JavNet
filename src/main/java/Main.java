import ndata.Data;
import nnormal.Normalizer;

import java.util.ArrayList;

/**
 * @author Jaroslaw Janas
 * @date 02/12/20
 * @project NN-Perceptron
 */
public class Main {
    public static void main(String[] args) {
        String filePath = "temp/train.txt";
        String delimiter = "\t";
        int classColumn = 3;
//        int inputSize = 5;
//        int hiddenLayerCount = 1000;
//        int layerSize = 500;
//        int outputSize = 6;
//
//        double[][] testInputs = {
//                {-2},
//                {-2},
//                {-2},
//                {-2},
//                {-2}
//        };
//
//        try {
//            LayerManager manager = new LayerManager(inputSize, hiddenLayerCount, layerSize, outputSize);
//            manager.initAllRandom();
////            manager.initAllNumbers();
//            Matrix results = manager.evaluateAll(new Matrix(testInputs));
//            results.print();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        formatter.printColumns();

        Data trainFile = new Data(filePath, delimiter, 3);
        ArrayList<String> trainingClasses = trainFile.getClasses();
        ArrayList<ArrayList<Double>> trainingData = trainFile.getData();

        Normalizer normalizer = new Normalizer(trainingData, -1, 1);
        trainingData = normalizer.normalize(trainingData);

        Data testFile = new Data(filePath, delimiter, classColumn);
        ArrayList<String> testingClasses = testFile.getClasses();
        ArrayList<ArrayList<Double>> testingData = testFile.getData();
//        note that the same normalizer is used
        testingData = normalizer.normalize(testingData);

        for(ArrayList<Double> column : trainingData){
            for(Double element : column){
                System.out.print(element + "] [");
            }
            System.out.println("");
        }
    }
}
