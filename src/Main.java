public class Main {
    public static void main(String[] args) {
        int size=50;
        double learningRate=0.1;
        NeuralNetwork neuralNetwork = new NeuralNetwork(3,10,2, learningRate);
        Dataset dataset = new Dataset(size);
        dataset.fillUpDataset();
        dataset.normalizeDataset();
        dataset.getTrainAndTestDataset(8);
        Train tr = new Train(neuralNetwork, dataset);
        tr.train(0.06);
        tr.test();























    }
}
