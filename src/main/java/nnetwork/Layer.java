package nnetwork;

/**
 * @author Jaroslaw Janas
 * @date 02/12/20
 * @project NN-Perceptron
 */
public class Layer {
    private Matrix weights;
    private Matrix biases;
    private Matrix inputs;
    private Matrix outputBeforeSigmoid;

    Layer(int nodeCount, int inputCount) {
        setWeights(new Matrix(nodeCount, inputCount));
        setBiases(new Matrix(nodeCount, 1));
    }

    public void initRandom(double min, double max) {
        weights.initRandom(min, max);
        biases.initRandom(min, max);

    }

    public void initRandom() {
        initRandom(-1, 1);
    }

    public void initNumbers() {
        weights.initNumbers();
        biases.initNumbers();
    }

    public void initWith(double d) {
        weights.initWith(d);
        biases.initWith(d);
    }

    public void initWithMatrices(Matrix weights, Matrix biases) {
        this.setWeights(weights);
        this.setBiases(biases);
    }

    public Matrix evaluate() throws Exception {
        Matrix weightedInputs = weights.dotProduct(inputs);
        Matrix biasedWeightedInputs = weightedInputs.add(biases);
        outputBeforeSigmoid = biasedWeightedInputs;

        if (biasedWeightedInputs.getColumns() != 1) {
            throw new Exception("A layer output must only have 1 column");
        }

        return biasedWeightedInputs.sigmoid();
    }

    private double sigmoid(double x) {
//        1/(1+e^-x)
        return 1 / (1 + Math.pow(Math.E, -x));
    }

    public Matrix getInputs() {
        return inputs;
    }

    public void setInputs(Matrix inputs) {
        this.inputs = inputs;
    }

    public Matrix getWeights() {
        return weights;
    }

    public Matrix getBiases() {
        return biases;
    }

    public void setWeights(Matrix weights) {
        this.weights = weights;
    }

    public void setBiases(Matrix biases) {
        this.biases = biases;
    }

    public Matrix getOutputBeforeSigmoid() {
        return outputBeforeSigmoid;
    }
}
