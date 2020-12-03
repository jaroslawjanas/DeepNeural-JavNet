import java.util.ArrayList;

/**
 * @author Jaroslaw Janas
 * @date 03/12/20
 * @project NN-Perceptron
 */
public class LayerManager {
    private final ArrayList<Layer> layers = new ArrayList<>();

    LayerManager(int inputSize, int hiddenLayerCount, int layerSize, int outputSize) throws Exception {
//        if layerCount is less than 1, no hidden layers, only output;
        if(hiddenLayerCount<1){
            throw new Exception("layerCount must be at least 1");
        }

//        if hiddenLayerCount is 1 two layers will be created
//        the 1 hidden layer and the output layer
//        there is no input layer since it's simply a vector
//        processed by the hidden layer
        for(int i=0; i<hiddenLayerCount+1; i++){
            if(i==0){
//                first hidden layer
                Layer layer = new Layer(inputSize, layerSize);
                layers.add(layer);
            }
            else if(i==hiddenLayerCount){
//                last output layer
                Layer layer = new Layer(layerSize, outputSize);
                layers.add(layer);
            }
            else {
//                hidden layers
                Layer layer = new Layer(layerSize, layerSize);
                layers.add(layer);
            }
        }
    }

    public void initAllRandom(){
        for(Layer layer : layers){
            layer.initRandom();
        }
    }

    public void initAllNumbers(){
        for(Layer layer : layers){
            layer.initNumbers();
        }
    }

//    public Matrix evaluate(Matrix inputs){
//
//    }

    public ArrayList<Layer> getLayers(){
        return layers;
    }
}
