package nnormal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * @author Jaroslaw Janas
 * @date 05/12/20
 * @project NN-Perceptron
 */
public class NormalizerTest {
    ArrayList<ArrayList<Double>> data;

    @Before
    public void setup() {
        data = new ArrayList<>();
        ArrayList<Double> column = new ArrayList<>();
        column.add(-123.3434);
        column.add(122.11);
        column.add(43.4124);
        column.add(0.0);
        data.add(column);
    }

    @Test
    public void newNormalizer() {
        Normalizer normalizer = new Normalizer(data, 0, 0);
        ArrayList<Double> maxData = normalizer.getDataMax();
        ArrayList<Double> minData = normalizer.getDataMin();

        Assert.assertEquals(122.11, maxData.get(0), 0.0001);
        Assert.assertEquals(-123.3434, minData.get(0), 0.0001);
    }

    @Test
    public void normalizationInRange() {
        Normalizer normalizer = new Normalizer(data, -2, 2);
        ArrayList<ArrayList<Double>> normalizedData = normalizer.normalize(data);

        Assert.assertEquals(-2, normalizedData.get(0).get(0), 0.00001); //-123.3434
        Assert.assertEquals(+2, normalizedData.get(0).get(1), 0.00001); //+122.11
        Assert.assertEquals(0.01004997283, normalizedData.get(0).get(3), 0.00000000001); //0
    }

    @Test
    public void normalizationOutOfRange() {
        Normalizer normalizer = new Normalizer(data, -2, 2);

//        out of range data
        ArrayList<ArrayList<Double>> outOfRangeData = new ArrayList<>();
        ArrayList<Double> column = new ArrayList<>();
        column.add(-200.0);
        column.add(0.0);
        column.add(500.0);
        ;
        outOfRangeData.add(column);

        ArrayList<ArrayList<Double>> normalizedData = normalizer.normalize(outOfRangeData);

        Assert.assertEquals(-3.249224496, normalizedData.get(0).get(0), 0.000000001); //-200.0
        Assert.assertEquals(0.01004997283, normalizedData.get(0).get(1), 0.00000000001); //0
        Assert.assertEquals(8.158236146, normalizedData.get(0).get(2), 0.000000001); //500.0
    }
}
