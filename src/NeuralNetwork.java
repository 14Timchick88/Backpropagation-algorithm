public class NeuralNetwork {
    private Layer inputLayer;
    private Layer hiddenLayer;
    private Layer outputLayer;
    private int numInputLayers;
    private int numHiddenLayers;
    private int numOutputLayers;
    private double[] inputLayerOutput;
    private double[] hiddenLayerOutput;
    private double[] outputLayerOutput;
    double learningRate;

    public NeuralNetwork(int numberInputLayers, int numberHiddenLayers, int numberOutputLayers, double learningRate) {
        numInputLayers = numberInputLayers;
        numHiddenLayers = numberHiddenLayers;
        numOutputLayers = numberOutputLayers;
        inputLayer = new InputLayer(numberInputLayers);
        hiddenLayer = new Layer(numberHiddenLayers,numberInputLayers);
        outputLayer = new Layer(numberOutputLayers, numberHiddenLayers);
        this.learningRate = learningRate;
    }

    public double [] fitForward(double[] input){
        inputLayerOutput  = inputLayer.fitForward(input);
        hiddenLayerOutput=hiddenLayer.fitForward(inputLayerOutput);
        outputLayerOutput = outputLayer.fitForward(hiddenLayerOutput);
        return outputLayerOutput;
    }


    private void fitBackwardLastLayer(double[] imageOutput){
        Neuron [] lastLayerNeurons = outputLayer.getNeurons();
        Neuron temp;
        double[] tempW;
        for (int neuron=0; neuron<lastLayerNeurons.length; neuron++){
            temp = lastLayerNeurons[neuron];
            tempW = temp.getWeights();
            for(int w=0; w<tempW.length; w++){
                tempW[w] -= learningRate*temp.getDerivative()*hiddenLayerOutput[w]*(outputLayerOutput[neuron]-imageOutput[neuron]);
            }
            temp.changeWeight(tempW);
        }
    }

    private void fitBackwardHiddenLayer(double[] imageOutput){
        Neuron [] hiddenLayerNeurons = hiddenLayer.getNeurons();
        Neuron temp;
        double[] tempW;
        double de_dy;
        Neuron [] out = outputLayer.getNeurons();

        for (int neuron=0; neuron<hiddenLayerNeurons.length; neuron++){
            de_dy =0;
            temp = hiddenLayerNeurons[neuron];
            tempW = temp.getWeights();
            for(int i=0; i<numOutputLayers; i++){
                de_dy += (outputLayerOutput[i]- imageOutput[i])*(out[i].getDerivative())*(out[i].getWeights()[neuron]);
            }
            for(int w=0; w<tempW.length; w++){
                tempW[w] -= learningRate*temp.getDerivative()*inputLayerOutput[w]*de_dy;
            }
            temp.changeWeight(tempW);
        }
    }


    public void fitBackward(double[] correct){
        fitBackwardLastLayer(correct);
        fitBackwardHiddenLayer(correct);
    }

}
