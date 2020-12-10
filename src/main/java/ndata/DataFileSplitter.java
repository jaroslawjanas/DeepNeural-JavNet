package ndata;

import java.util.ArrayList;

/**
 * @author Jaroslaw Janas
 * @date 10/12/20
 * @project NN-Perceptron
 */
public class DataFileSplitter {
    private ArrayList<ArrayList<Double>> trainingData;
    private ArrayList<String> trainingClasses;

    private ArrayList<ArrayList<Double>> testingData;
    private ArrayList<String> testingClasses;

    public DataFileSplitter(String filePath, String delimiter, int classColumn) {
        try {
//            import
            DataImporter importedData = new DataImporter(filePath);
            DataFormatter formatter = new DataFormatter(importedData.getLines());

//            shuffle
            formatter.shuffleLines();

//            split
//            extracts 33% of the data, leaves the remaining 77%
            ArrayList<String> extractedData = formatter.splitAndExtract(0.33);
//            assign the extracted data to testing
            DataFormatter testing = new DataFormatter(extractedData);
//            re-name the formatter for clarity, it contains remaining 77% data
            DataFormatter training = formatter;

//            format - training
            training.setDelimiter(delimiter);
            training.format();
            trainingClasses = training.extractClassColumn(classColumn);
            training.parseToDouble();
            trainingData = training.getColumnsDouble();

//            format - testing
            testing.setDelimiter(delimiter);
            testing.format();
            testingClasses = testing.extractClassColumn(classColumn);
            testing.parseToDouble();
            testingData = testing.getColumnsDouble();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ArrayList<Double>> getTrainingData() {
        return trainingData;
    }

    public ArrayList<ArrayList<Double>> getTestingData() {
        return testingData;
    }

    public ArrayList<String> getTrainingClasses() {
        return trainingClasses;
    }

    public ArrayList<String> getTestingClasses() {
        return testingClasses;
    }

}
