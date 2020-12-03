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
    public void allLayerCount(){
        System.out.println("TEST");
        int allLayerCount = manager.getLayers().size();
        System.out.println(allLayerCount);
        Assert.assertEquals(6, allLayerCount);
    }

    @Test
    public void layerInitRandom(){
        manager.initAllNumbers();
        Layer layer = manager.getLayers().get(0);
        double [][] weights = layer.getWeights().getRaw();
        double [][] biases = layer.getBiases().getRaw();

        Assert.assertEquals(1, (int) weights[0][0]);
        Assert.assertEquals(1, (int) biases[0][0]);
    }
}
