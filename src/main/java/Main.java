/**
 * @author Jaroslaw Janas
 * @date 02/12/20
 * @project NN-Perceptron
 */
public class Main {
    public static void main(String[] args) {
        int inputSize = 5;
        int hiddenLayerCount = 20;
        int layerSize = 16;
        int outputSize = 6;

        double[][] testInputs = {
                {2},
                {5},
                {6},
                {12},
                {7}
        };

        try {
            LayerManager manager = new LayerManager(inputSize, hiddenLayerCount, layerSize, outputSize);
            manager.initAllRandom();
            Matrix results = manager.evaluateAll(new Matrix(testInputs));
            results.print();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
