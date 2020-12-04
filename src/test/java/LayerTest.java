import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Jaroslaw Janas
 * @date 03/12/20
 * @project NN-Perceptron
 */
public class LayerTest {

    Layer layer;

    @Before
    public void setup() {
        layer = new Layer(10, 2);
    }

    @Test
    public void evaluateTest() {
        layer.initNumbers();

        double[][] inputs = {{1}, {2}};
        Matrix inputMatrix = new Matrix(inputs);

        layer.setInputs(inputMatrix);
        Matrix outputMatrix = null;

        try {
            outputMatrix = layer.evaluate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertNotNull(outputMatrix);
        double[][] output = outputMatrix.getRaw();

        Assert.assertEquals(0.9975273768, output[0][0], 0.000000001);
    }
}
