public class InputNeuron extends Neuron {

    public InputNeuron() {
        super();
        weights = new double[1];
        weights[0] = 1;
        numberInputs = 1;
    }

    @Override
    protected double activationFunc(double x) {
        return x;
    }


}
