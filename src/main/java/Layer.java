/**
 * @author Jaroslaw Janas
 * @date 02/12/20
 * @project NN-Perceptron
 */
public class Layer {
    int inputs, nodes;
    Matrix weights;
    Matrix biases;

    Layer(int inputs, int nodes){
        this.inputs = inputs;
        this.nodes = nodes;
    }

    public void initRandom(){
        weights = new Matrix(inputs, nodes);
        weights.initRandom();

        biases = new Matrix(nodes, 1);
        biases.initRandom();
    }




}
