import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Train {
    NeuralNetwork network;
    Dataset dataset;

    public Train(NeuralNetwork network, Dataset dataset) {
        this.network = network;
        this.dataset = dataset;

    }

    public double getError(double[] res_out, double[] cor_output) {
        double error = 0;
        for (int i = 0; i < cor_output.length; i++) {
            error += (cor_output[i] - res_out[i]) * (cor_output[i] - res_out[i]);
        }
        return error;
    }

    public double getError2(double[] res_out, double[] cor_output) {
        double error = 0;
        double d=0;
        for (int i = 0; i < cor_output.length; i++) {
            error += (cor_output[i] - res_out[i]) * (cor_output[i] - res_out[i]);
            d+=res_out[i]*res_out[i];
        }
        return error/d;
    }


    public void train(double eps) {
        double[][] trainData = dataset.getTrainData();
        double[][] inputTrain = dataset.getArguments(trainData);
        double[][] outputTrain = dataset.getOutput(trainData);
        double[] res;
        double meanErr = 100;
        List<Integer> indices = new ArrayList<>();
        List<Double> errorDependIteration = new ArrayList<>();

        for (int i = 0; i < trainData.length; i++) {
            indices.add(i);
        }
        int epoh = 0;
        while (meanErr > eps && epoh <= 5000) {
            meanErr = 0;
            epoh++;

            Random rand = new Random();
            java.util.Collections.shuffle(indices, rand);
            for (int i = 0; i < indices.size(); i++) {
                res = network.fitForward(inputTrain[indices.get(i)]);
                meanErr += getError(res, outputTrain[indices.get(i)]);
                network.fitBackward(outputTrain[indices.get(i)]);
            }
            meanErr /= trainData.length;
            errorDependIteration.add(meanErr);


        }
        System.out.println("Нейромережа навчилася з точністю "+ meanErr);
        System.out.println("Кількість ітерацій: "+epoh);

//        for (int i = 0; i < errorDependIteration.size(); i++) {
//           System.out.print(errorDependIteration.get(i) + ", ");
//        }
    }



    public void test() {
        double[][] testData = dataset.getTestData();
        double[][] inputTest = dataset.getArguments(testData);
        double[][] outputTest = dataset.getOutput(testData);
        double[][] res = new double[inputTest.length][2];
        for (int i = 0; i < inputTest.length; i++) {
            res[i] = network.fitForward(inputTest[i]);
        }
        double[][] ot = new double[inputTest.length][testData[0].length];
        for (int i = 0; i < ot.length; i++) {
            for (int j = 0; j < ot[0].length; j++) {
                if (j<3){
                    ot[i][j] = inputTest[i][j];
                }else {
                    ot[i][j] = res[i][j-3];
                }
            }
        }
        double mm=0;
        for (int i = 0; i < res.length; i++) {
            mm += getError(res[i], outputTest[i]);
        }
        mm /= res.length;

        System.out.println("Похибка на контрольній вибірці: "+mm);

        System.out.println("Табличні дані");
        dataset.outArray(testData);
        System.out.println();
        System.out.println("Результат роботи.");
        dataset.outArray(ot);

        System.out.println();
        System.out.println("Ненормалізовані дані");
        dataset.denormalizeTest(testData);
        dataset.outArray(dataset.getTestData());

        System.out.println();
        System.out.println("Прогнозовані дані");

        dataset.denormalizeTest(ot);
        dataset.outArray(ot);

    }


}
