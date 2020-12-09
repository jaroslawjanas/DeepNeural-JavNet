package nnetwork;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Jaroslaw Janas
 * @date 03/12/20
 * @project NN-Perceptron
 */
public class LayerManager {
    private final ArrayList<Layer> layers = new ArrayList<>();

    public LayerManager(int inputSize, int hiddenLayerCount, int layerSize, int outputSize) throws Exception {
//        if layerCount is less than 1, no hidden layers, only output;
//        if (hiddenLayerCount < 0) {
//            throw new Exception("layerCount must be at least 1");
//        }

//        if hiddenLayerCount is 1 two layers will be created
//        the 1 hidden layer and the output layer
//        there is no input layer since it's simply a vector
//        processed by the hidden layer
        for (int i = 0; i < hiddenLayerCount + 1; i++) {

            if(i == 0 && i == hiddenLayerCount){
//                if first and last layer
                Layer layer = new Layer(outputSize, inputSize);
                layers.add(layer);
            } else if (i == 0) {
//                first hidden layer
                Layer layer = new Layer(layerSize, inputSize);
                layers.add(layer);
            } else if (i == hiddenLayerCount) {
//                last output layer
                Layer layer = new Layer(outputSize, layerSize);
                layers.add(layer);
            } else {
//                hidden layers
                Layer layer = new Layer(layerSize, layerSize);
                layers.add(layer);
            }
        }
    }

    public void initAllRandom() {
        initAllRandom(-1, 1);
    }

    public void initAllRandom(double min, double max) {
        for (Layer layer : layers) {
            layer.initRandom(min, max);
        }
    }

    public void initAllNumbers() {
        for (Layer layer : layers) {
            layer.initNumbers();
        }
    }

    public void initAllWith(double d) {
        for (Layer layer : layers) {
            layer.initWith(d);
        }
    }

    public Matrix feedForward(Matrix inputs) throws Exception {
        Matrix outputs = inputs;
        for (Layer layer : layers) {
            layer.setInputs(outputs);
            outputs = layer.evaluate();
        }
        return outputs;
    }

    public void backPropagate(Matrix predicted, Matrix expected, double learningRate) {

        Matrix dC0_daLm1 = null;

        for(int l=layers.size()-1; l>=0; l--){
            Layer currentLayer = layers.get(l);

            Matrix currentWeights = currentLayer.getWeights();
            Matrix currentInputs = currentLayer.getInputs();
            Matrix currentBiases = currentLayer.getBiases();
            Matrix currentOutputBeforeSigmoid = currentLayer.getOutputBeforeSigmoid();

            //if output layer
            if(l == layers.size()-1) {

                try {
                    Matrix dC0_daL = predicted.subtract(expected).multiply(2);
                    Matrix daL_dzL = currentOutputBeforeSigmoid.sigmoidDerivative();
//                    base used for all other equations
                    Matrix dC0_dzL = dC0_daL.hadamardProduct(daL_dzL);

//                    weights
                    Matrix dC0_dwL = dC0_dzL.dotProduct(currentInputs.transpose());
                    Matrix updatedWeights = currentWeights.subtract(dC0_dwL.multiply(learningRate));
                    currentLayer.setWeights(updatedWeights);

//                    biases
                    Matrix dC0_dbL = dC0_dzL.multiply(learningRate);
//                    because dC0_dzL * 1 = dC0_dzL; don't forget about learning rate

                    Matrix updatedBiases = currentBiases.add(dC0_dbL);
                    currentLayer.setBiases(updatedBiases);

//                    next (going backwards) layer
                    dC0_daLm1 = currentWeights.transpose().dotProduct(dC0_dzL);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else {
                try {
//                    base
                    Matrix dam1_zm1 = currentOutputBeforeSigmoid.sigmoidDerivative();
                    assert dC0_daLm1 != null;
                    Matrix dC0_dzm1 = dC0_daLm1.hadamardProduct(dam1_zm1);

//                    weights
                    Matrix daLm1_dwLm1 = dC0_dzm1.dotProduct(currentInputs.transpose());
                    Matrix updatedWeights = currentWeights.subtract(daLm1_dwLm1.multiply(learningRate));
                    currentLayer.setWeights(updatedWeights);

//                    biases
                    Matrix updatedBiases = dC0_dzm1.multiply(learningRate);
                    currentLayer.setBiases(updatedBiases);

//                    next (going backwards) layer,
//                    don't calculate daLm1_daLm2 if last layer (going backwards)
                    if(l != 0){
                        dC0_daLm1 = currentWeights.transpose().dotProduct(dC0_daLm1);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public ArrayList<Layer> getLayers() {
        return layers;
    }
}
