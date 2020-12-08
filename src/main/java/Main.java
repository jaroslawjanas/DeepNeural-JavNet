import ndata.DataFile;
import nnetwork.LayerManager;
import nnetwork.Matrix;
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
        int inputSize = 9;
        int hiddenLayerCount = 1;
        int layerSize = 16;
        int outputSize = 3;


        LayerManager layerManager = null;
        try {
            layerManager = new LayerManager(inputSize, hiddenLayerCount, layerSize, outputSize);
            layerManager.initAllRandom();
//            manager.initAllNumbers();
        } catch (Exception e) {
            e.printStackTrace();
        }

//        formatter.printColumns();

        DataFile trainFile = new DataFile(filePath, delimiter, 3);
        ArrayList<String> trainingClasses = trainFile.getClasses();
        ArrayList<ArrayList<Double>> trainingData = trainFile.getData();

//        Normalizer normalizer = new Normalizer(trainingData, -1, 1);
//        trainingData = normalizer.normalize(trainingData);

        DataFile testFile = new DataFile(filePath, delimiter, classColumn);
        ArrayList<String> testingClasses = testFile.getClasses();
        ArrayList<ArrayList<Double>> testingData = testFile.getData();
//        note that the same normalizer is used
//        testingData = normalizer.normalize(testingData);

//        //training
//        for (int row = 0; row < trainingData.get(0).size(); row++) {
//
//            double[][] rowData = new double[trainingData.size()][1];
//            for (int col = 0; col < trainingData.size(); col++) {
//                rowData[col][0] = trainingData.get(col).get(row);
//            }
//            Matrix input = new Matrix(rowData);
//            try {
//                assert layerManager != null;
//                Matrix predicted = layerManager.feedForward(input);
////                mock
//                double[][] expected = {{1}, {0}, {0}};
//                layerManager.backPropagate(predicted, new Matrix(expected), 0.1);
//                System.out.println(predicted);
////                break;
////                ----
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }

        for (int row = 0; row < trainingData.get(0).size(); row++) {

            double[][] rowData = new double[trainingData.size()][1];
            for (int col = 0; col < trainingData.size(); col++) {
                rowData[col][0] = trainingData.get(col).get(row);
            }
            Matrix input = new Matrix(rowData);
            for(int i = 0; i< 10; i++) {
                try {
                    assert layerManager != null;
                    Matrix predicted = layerManager.feedForward(input);
//                mock
                    double[][] expected = {{1}, {0}, {0}};
                    layerManager.backPropagate(predicted, new Matrix(expected), 0.4);
                    System.out.println(predicted.subtract(new Matrix(expected)));
                    System.out.println("----------------");
//                break;
//                ----
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            break;
        }
    }
}
