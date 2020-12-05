import ndata.DataFormatter;
import ndata.DataImporter;
import nnetwork.LayerManager;
import nnetwork.Matrix;

import java.util.ArrayList;

/**
 * @author Jaroslaw Janas
 * @date 02/12/20
 * @project NN-Perceptron
 */
public class Main {
    public static void main(String[] args) {
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
        try {
            DataImporter importedData = new DataImporter("temp/dataset.txt");
            DataFormatter formatter = new DataFormatter(importedData.getLines());
            formatter.setDelimiter("\t");
            formatter.format();
            ArrayList<String> classes = formatter.extractClassColumn(3);
            formatter.parseToDouble();
            ArrayList<ArrayList<Double>> data = formatter.getColumnsDouble();

            for (ArrayList<Double> column : data) {
                for (Double element : column) {
                    System.out.print(element + " ");
                }
                System.out.println("");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        formatter.printColumns();
    }
}
