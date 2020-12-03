/**
 * @author Jaroslaw Janas
 * @date 02/12/20
 * @project NN-Perceptron
 */
public class Main {
    public static void main(String[] args) throws Exception {
        int inputs = 5;
        int layers = 1;
        int layerSize = 3;
        int outputs = 2;

        double[][] testInputs = {
                {2},
                {5},
                {6},
                {12},
                {7}
        };

        LayerManager manager = new LayerManager(inputs, layers, layerSize, outputs);
    }
}
