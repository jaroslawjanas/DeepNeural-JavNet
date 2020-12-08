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
        if (hiddenLayerCount < 1) {
            throw new Exception("layerCount must be at least 1");
        }

//        if hiddenLayerCount is 1 two layers will be created
//        the 1 hidden layer and the output layer
//        there is no input layer since it's simply a vector
//        processed by the hidden layer
        for (int i = 0; i < hiddenLayerCount + 1; i++) {
            if (i == 0) {
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

    public Matrix feedForward(Matrix inputs) throws Exception {
        Matrix outputs = inputs;
        for (Layer layer : layers) {
            layer.setInputs(outputs);
            outputs = layer.evaluate();
        }
        return outputs;
    }

    public void backPropagate(Matrix predicted, Matrix expected, double learningRate) {

        Matrix deltaCostFunction_PrevActivation = null;
        for(int l=layers.size()-1; l>=0; l--){
            Layer currentLayer = layers.get(l);

            //if output layer
            if(l == layers.size()-1) {
                Matrix currentWeights = currentLayer.getWeights();
                Matrix currentInputs = currentLayer.getInputs();
                Matrix currentBiases = currentLayer.getBiases();
                Matrix outputBeforeSigmoid = currentLayer.getOutputBeforeSigmoid();

                try {
                    Matrix dC0_daL = predicted.subtract(expected).multiply(2);
                    Matrix daL_dzL = outputBeforeSigmoid.sigmoidDerivative();
//                    base used for all other equations
                    Matrix dC0_dzL = dC0_daL.hadamardProduct(daL_dzL);

//                    weights
                    Matrix dC0_dwL = dC0_dzL.dotProduct(currentInputs.transpose());
                    Matrix updatedWeights = currentWeights.subtract(dC0_dwL.multiply(learningRate));
                    currentLayer.setWeights(updatedWeights);

//                    //biases
//                    Matrix dC0_dbL = dC0_dzL; //because dC0_dzL * 1 = dC0_dzL
//                    Matrix updatedBiases = currentBiases.add(dC0_dbL);
//                    currentLayer.setBiases(updatedBiases);
//
//                    //next (going backwards) layer
//                    Matrix dC0_daLm1 = dC0_dzL.hadamardProduct(currentWeights);
//                    deltaCostFunction_PrevActivation = dC0_daLm1;

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else {
                break;
            }
        }
    }

    public ArrayList<Layer> getLayers() {
        return layers;
    }
}
