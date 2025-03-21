import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Dataset {
    private int numberOfSamples;
    private final int numberOfArguments = 3;
    private final int numberOfOutput = 2;
    private final int numberOfBins = 5;
    private double funcMean = 0;
    private double[][] data;
    private double[][] trainData;
    private double[][] testData;
    private double[] maxByColumn;
    private double[] minByColumn;


    public Dataset(int numberOfSamples) {
        this.numberOfSamples = numberOfSamples;
        data = new double[numberOfSamples][numberOfBins];
        maxByColumn = new double[numberOfBins];
        minByColumn = new double[numberOfBins];
        data[0][0] = 1;
        data[0][1] = 2;
        data[0][2] = 3;
    }




    private double function(double x1, double x2, double x3) {
        return (x1 * x1) - (x2 * x2) + (x3 * x3);
    }

    public int getNumberOfSamples() {
        return numberOfSamples;
    }

    public int getNumberOfArguments() {
        return numberOfArguments;
    }

    public int numberOfOutput() {
        return numberOfOutput;
    }

    private int getStep() {
        double randomNumber = Math.random();
        String number = String.valueOf(randomNumber).substring(2);
        int step = 0;

        for (int i = 0; i < number.length(); i++) {
            if ((number.charAt(i) == '0' || number.charAt(i) == '1' || number.charAt(i) == '2' || number.charAt(i) == '3') && i % 2 == 0) {
                step = (number.charAt(i) - '0');
                break;

            } else if ((number.charAt(i) == '0' || number.charAt(i) == '1' || number.charAt(i) == '2' || number.charAt(i) == '3') && i % 2 == 1) {
                step = -(int) (number.charAt(i) - '0');
                break;

            }
        }
        return step;
    }


    private void fillUpArguments() {
        for (int i = 1; i < numberOfSamples; i++) {
            for (int j = 0; j < numberOfArguments; j++) {
                data[i][j] = data[0][j] + getStep();

            }
        }

    }

    public double[][] getArguments(double[][]array) {
        double[][] arguments = new double[array.length][numberOfArguments];
        for (int sample = 0; sample < array.length; sample++) {
            for (int bin = 0; bin < numberOfArguments; bin++) {
                arguments[sample][bin] = array[sample][bin];
            }
        }
        return arguments;
    }

    public double[][] getOutput(double[][]array) {
        double[][] output = new double[array.length][numberOfOutput];
        for (int sample = 0; sample < array.length; sample++) {
            for (int bin = numberOfArguments; bin < numberOfBins; bin++) {
                output[sample][bin - numberOfOutput - 1] = array[sample][bin];
            }
        }
        return output;

    }

    public void outArray(double[][] array) {
        System.out.println("-----------------------------------------------");
        System.out.printf("%-10s %-10s %-10s %-10s %-10s%n", "X1", "X2", "X3", "D1", "D2");
        System.out.println("-----------------------------------------------");
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                System.out.printf("%-10f", array[i][j]);
            }
            System.out.println();
        }
    }


    private void fillUpFunctionOutput() {
        for (int i = 1; i < numberOfSamples; i++) {
            data[i][3] = function(data[i][0], data[i][1], data[i][2]);
        }
    }


    private void fillUpLastOutput() {
        double mean = 0;
        for (int i = 0; i < numberOfSamples; i++) {
            mean += data[i][3];
        }
        mean /= numberOfSamples;
        for (int i = 0; i < numberOfSamples; i++) {
            if (data[i][3] > mean) {
                data[i][4] = 1;
            } else {
                data[i][4] = 0;
            }
        }
        funcMean = mean;
        System.out.println("Середнє: " + mean);

    }


    public void fillUpDataset() {
        fillUpArguments();
        fillUpFunctionOutput();
        fillUpLastOutput();
    }


    public void normalizeDataset() {
        double[] temp = new double[numberOfSamples];
        for (int row = 0; row < data[0].length; row++) {
            for (int i = 0; i < numberOfSamples; i++) {
                temp[i] = data[i][row];
            }
            maxByColumn[row] = getMax(temp);
            minByColumn[row] = getMin(temp);
            normalizeColum(temp);
            for (int k = 0; k < numberOfSamples; k++) {
                data[k][row] = temp[k];
            }

        }


    }


    public double[][] getData() {
        return data;
    }


    private double getMax(double[] arr) {
        double max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        return max;
    }


    private double getMin(double[] arr) {
        double min = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < min) {
                min = arr[i];
            }
        }
        return min;
    }

    private void normalizeColum(double[] col) {
        double mn = getMin(col);
        double mx = getMax(col);
        for (int i = 0; i < col.length; i++) {
            col[i] = (col[i] - mn) / (mx - mn);
        }
    }

    public void denormalizeTest(double[][]test) {
        for (int row = 0; row < numberOfBins; row++) {
            for (int col = 0; col < testData.length; col++) {
                test[col][row] = test[col][row] * (maxByColumn[row] - minByColumn[row]) + minByColumn[row];
            }
        }
    }

    public String denormalizeOneResult(double []res) {
        String result = "";
        result +=  res[0] * (maxByColumn[numberOfArguments] - minByColumn[numberOfArguments]) + minByColumn[numberOfArguments]+" ";
        result +=  res[1] * (maxByColumn[numberOfArguments+1] - minByColumn[numberOfArguments+1]) + minByColumn[numberOfArguments+1];
        return result;


    }

    public void getTrainAndTestDataset(int train) {
        int trainSize = (int) Math.round(numberOfSamples * (train / 10.0));
        int testSize = numberOfSamples - trainSize;

        trainData = new double[trainSize][numberOfBins];
        testData = new double[testSize][numberOfBins];

        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < numberOfSamples; i++) {
            indices.add(i);
        }

        Random rand = new Random();
        java.util.Collections.shuffle(indices, rand);

        for (int i = 0; i < trainSize; i++) {
            trainData[i] = data[indices.get(i)];
        }
        for (int i = 0; i < testSize; i++) {
            testData[i] = data[indices.get(i + trainSize)];
        }
    }


    public double[][] getTestData() {
        return testData;
    }

    public double[][] getTrainData() {
        return trainData;
    }


}
