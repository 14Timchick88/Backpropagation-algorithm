public class Layer {
    protected int numberNeuronInLayer;
    protected Neuron[] neurons;

    public Layer() {
        neurons = null;
        numberNeuronInLayer = 0;
    }

    public Layer(int numberNeurons, int numberInputs) {
        numberNeuronInLayer = numberNeurons;
        neurons = new Neuron[numberNeuronInLayer];
        for (int i = 0; i < numberNeuronInLayer; i++) {
            neurons[i] = new Neuron(numberInputs);
        }
    }

    public double[] fitForward(double[] inputs) {
        double[] outputs = new double[neurons.length];
        for (int i = 0; i < neurons.length; i++) {
            outputs[i] = neurons[i].activate(inputs);
        }
        return outputs;
    }


    public Neuron[] getNeurons() {
        return neurons;
    }
}
