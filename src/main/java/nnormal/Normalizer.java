package nnormal;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Jaroslaw Janas
 * @date 05/12/20
 * @project NN-Perceptron
 */

//based on https://www.baeldung.com/cs/normalizing-inputs-artificial-neural-network
public class Normalizer {
    private ArrayList<Double> dataMin = new ArrayList<>();
    private ArrayList<Double> dataMax = new ArrayList<>();
    private double lowerLimit;
    private double upperLimit;

    public Normalizer(ArrayList<ArrayList<Double>> data, double lowerLimit, double upperLimit) {
//        creates a normalizer for the data
//        this allows for the same normalization (min and max values) of
//        the testing dataset, that might be in slightly different range

        this.lowerLimit = lowerLimit;
        this.upperLimit = upperLimit;

        for (ArrayList<Double> column : data) {
            getDataMin().add(Collections.min(column));
            getDataMax().add(Collections.max(column));
        }
    }

    public ArrayList<ArrayList<Double>> normalize(ArrayList<ArrayList<Double>> data) {
        ArrayList<ArrayList<Double>> normalizedData = new ArrayList<>();
        int i = 0;
        for (ArrayList<Double> column : data) {
            double cMin = getDataMin().get(i);
            double cMax = getDataMax().get(i);

            ArrayList<Double> normC = new ArrayList<>();
            for (Double element : column) {
                Double normElement = ((element - cMin) / (cMax - cMin)) * (upperLimit - lowerLimit) + lowerLimit;
                normC.add(normElement);
            }
            normalizedData.add(normC);
            i++;
        }
        return normalizedData;
    }

    public ArrayList<Double> getDataMin() {
        return dataMin;
    }

    public ArrayList<Double> getDataMax() {
        return dataMax;
    }
}
