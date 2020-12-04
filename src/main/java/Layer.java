/**
 * @author Jaroslaw Janas
 * @date 02/12/20
 * @project NN-Perceptron
 */
public class Layer {
    private Matrix weights;
    private Matrix biases;
    private Matrix inputs;

    Layer(int nodeCount, int inputCount){
        weights = new Matrix(nodeCount, inputCount);
        biases = new Matrix(nodeCount, 1);
    }

    public void initRandom(double min, double max){
        weights.initRandom(min, max);
        biases.initRandom(min, max);

    }
    public void initRandom(){
        initRandom(-1, 1);
    }

    public void initNumbers(){
        weights.initNumbers();
        biases.initNumbers();
    }

    public void initWithMatrices(Matrix weights, Matrix biases){
        this.weights = weights;
        this.biases = biases;
    }

    public Matrix evaluate() throws Exception {
        Matrix weightedInputs = weights.dotProduct(inputs);
        Matrix biasedWeightedInputs = weightedInputs.add(biases);

        if(biasedWeightedInputs.getColumns() != 1){
            throw new Exception("A layer output must only have 1 column");
        }

        double[][] bwInputs = biasedWeightedInputs.getRaw();
        double[][] outputArray = new double[bwInputs.length][1];
        for(int i=0; i<bwInputs.length; i++){
            outputArray[i][0] = sigmoid(bwInputs[i][0]);
        }

        return new Matrix(outputArray);
    }

    private double sigmoid(double x){
//        1/(1+e^-x)
        return 1 / (1 + Math.pow(Math.E, -x));
    }

    public void setInputs(Matrix inputs){
        this.inputs = inputs;
    }

    public Matrix getWeights(){
        return weights;
    }

    public Matrix getBiases(){
        return biases;
    }

}
