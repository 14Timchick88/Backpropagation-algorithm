public class InputLayer extends Layer {

    public InputLayer(int numberNeuronInLayer) {
        super();
        this.numberNeuronInLayer = numberNeuronInLayer;
        neurons = new InputNeuron[numberNeuronInLayer];
        for (int i = 0; i < numberNeuronInLayer; i++) {
            neurons[i] = new InputNeuron();
        }
    }
    @Override
    public double [] fitForward(double [] inputs) {
        double [] outputs = new double[neurons.length];
        for (int i = 0; i < neurons.length; i++) {
            outputs[i] = neurons[i].activate(new double[]{inputs[i]});
        }
        return outputs;
    }

}
