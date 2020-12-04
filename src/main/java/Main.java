/**
 * @author Jaroslaw Janas
 * @date 02/12/20
 * @project NN-Perceptron
 */
public class Main {
    public static void main(String[] args) {
        int inputSize = 5;
        int hiddenLayerCount = 1000;
        int layerSize = 500;
        int outputSize = 6;

        double[][] testInputs = {
                {-2},
                {-2},
                {-2},
                {-2},
                {-2}
        };

        try {
            LayerManager manager = new LayerManager(inputSize, hiddenLayerCount, layerSize, outputSize);
            manager.initAllRandom();
//            manager.initAllNumbers();
            Matrix results = manager.evaluateAll(new Matrix(testInputs));
            results.print();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
