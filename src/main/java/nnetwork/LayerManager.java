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

            if (i == 0 && i == hiddenLayerCount) {
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

/*
    The mathematical reference and explanation can be found in the maths folder
    in the maths.html and treeDiagram.html files
    
    The variables follow the following format
    d = symbol partial derivative
    _ = division
    m = minus (e.g. Lm1 = L-1)
    uppercase = going to superscript e.g dc0/daLm1 where (Lm1) is the superscript of value a, _ exits superscript

    Additionally, all numbers that are NOT in superscript (daLm1) such as dc0 are indexes

    --------------------------

    Matrix mathematics is based on https://sudeepraja.github.io/Neural/
 */

    public void backPropagate(Matrix predicted, Matrix expected, double learningRate) {

        Matrix dc0_daLm1 = null; //equivalent of dc0_daLm2 if in a hidden layer

        for (int l = layers.size() - 1; l >= 0; l--) {
            Layer currentLayer = layers.get(l);

            Matrix currentWeights = currentLayer.getWeights(); //wL
            Matrix currentInputs = currentLayer.getInputs(); //aLm1
            Matrix currentBiases = currentLayer.getBiases(); //bL
            Matrix currentOutputBeforeSigmoid = currentLayer.getOutputBeforeSigmoid(); //zL

            //if output layer
            if (l == layers.size() - 1) {

                try {
//                    base/common
                    Matrix dc0_daL = predicted.subtract(expected).multiply(2);
                    Matrix daL_dzL = currentOutputBeforeSigmoid.sigmoidDerivative();
                    Matrix dc0_dzL = dc0_daL.hadamardProduct(daL_dzL);

//                    weights
                    Matrix dc0_dwL = dc0_dzL.dotProduct(currentInputs.transpose());
                    Matrix updatedWeights = currentWeights.subtract(dc0_dwL.multiply(learningRate));
                    currentLayer.setWeights(updatedWeights);

//                    biases
                    Matrix dc0_dbL = dc0_dzL.multiply(learningRate);
//                    because dc0_dzL * 1 = dc0_dzL; don't forget about learning rate

                    Matrix updatedBiases = currentBiases.add(dc0_dbL);
                    currentLayer.setBiases(updatedBiases);

//                    next (going backwards) layer
                    dc0_daLm1 = currentWeights.transpose().dotProduct(dc0_dzL);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
//                    base/common
                    Matrix daLm1_dzLm1 = currentOutputBeforeSigmoid.sigmoidDerivative();
                    assert dc0_daLm1 != null;
                    Matrix dc0_dzLm1 = dc0_daLm1.hadamardProduct(daLm1_dzLm1);

//                    weights
                    Matrix dc0_dwLm1 = dc0_dzLm1.dotProduct(currentInputs.transpose()); //current inputs = aLm2
                    Matrix updatedWeights = currentWeights.subtract(dc0_dwLm1.multiply(learningRate));
                    currentLayer.setWeights(updatedWeights);

//                    biases
                    Matrix updatedBiases = dc0_dzLm1.multiply(learningRate);
                    currentLayer.setBiases(updatedBiases);

//                    next (going backwards) layer,
//                    don't calculate daLm1_daLm2 if last layer (going backwards)
                    if (l != 0) {
//                        equivalent of dc0_daLm2 if in a hidden layer
//                        but a common variable name (with output layer) is required hence dc0_daLm1
                        dc0_daLm1 = currentWeights.transpose().dotProduct(dc0_daLm1);
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
