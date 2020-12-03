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
    public void newManager() throws Exception {
        manager = new LayerManager(10, 5, 16, 3);
    }

    @Test
    public void allLayersCount(){
        int allLayerCount = manager.getLayers().size();
        Assert.assertEquals(6, allLayerCount);
    }

    @Test
    public void allLayersInitRandom(){
        manager.initAllNumbers();
        Layer layer = manager.getLayers().get(0);
        double [][] weights = layer.getWeights().getRaw();
        double [][] biases = layer.getBiases().getRaw();

        Assert.assertEquals(1, weights[0][0], 0.0001);
        Assert.assertEquals(1, biases[0][0], 0.0001);
    }
}
