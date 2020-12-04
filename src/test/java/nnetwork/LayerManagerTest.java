package nnetwork;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Jaroslaw Janas
 * @date 03/12/20
 * @project NN-Perceptron
 */
public class LayerManagerTest {
    private LayerManager manager;

    @Before
    public void newManager() {
        try {
            manager = new LayerManager(10, 5, 16, 3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void allLayersCount() {
        int allLayerCount = manager.getLayers().size();
        Assert.assertEquals(6, allLayerCount);
    }

    @Test
    public void allLayersInitNumbers() {
        manager.initAllNumbers();
        Layer layer = manager.getLayers().get(0);
        double[][] weights = layer.getWeights().getRaw();
        double[][] biases = layer.getBiases().getRaw();

        Assert.assertEquals(1, weights[0][0], 0.0001);
        Assert.assertEquals(1, biases[0][0], 0.0001);
    }

    @Test
    public void oneLayerEvaluation() {
        double[][] inputs = {
                {0.001},
                {-1}
        };
        Matrix input = new Matrix(inputs);

        try {
            manager = new LayerManager(2, 1, 3, 2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        manager.initAllNumbers();
        Matrix output = null;
        try {
            output = manager.evaluateAll(input);
        } catch (Exception e) {
            e.printStackTrace();
        }

        assert output != null;
        double[][] outputs = output.getRaw();
//        these were calculated manually
        Assert.assertEquals(0.9319097998756132563669, outputs[0][0], 0.000000000000001);
        Assert.assertEquals(0.9881605417259831341509, outputs[1][0], 0.000000000000001);

    }
}
