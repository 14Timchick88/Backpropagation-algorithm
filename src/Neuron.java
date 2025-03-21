public class Neuron {
    protected double[] weights;
    protected double output;
    protected int numberInputs;

    public Neuron() {
        weights = null;
        numberInputs = 0;
    }

    public Neuron(int numberWeights) {
        weights = new double[numberWeights];
        numberInputs = numberWeights;
        for (int i = 0; i < numberWeights; i++) {
            weights[i] = getRandomDouble();
        }
    }

    private double getRandomDouble() {
        return (int) (Math.random() * 100) / 100.0;
    }

    protected double activationFunc(double x) {
        return 1 / (1 + Math.exp(-x));
    }

    public double activate(double[] inputs) {
        double sum = 0;
        for (int i = 0; i < weights.length; i++) {
            sum += weights[i] * inputs[i];
        }
        output = activationFunc(sum);
        return output;
    }

    public void changeWeight(double[] newWeights) {
        weights = newWeights;
    }

    public double[] getWeights() {
        return weights;
    }

    public double getDerivative() {
        return output * (1 - output);
    }

}
