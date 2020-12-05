package ndata;

import java.util.ArrayList;

/**
 * @author Jaroslaw Janas
 * @date 05/12/20
 * @project NN-Perceptron
 */
public class Data {
    private ArrayList<ArrayList<Double>> data;
    private ArrayList<String> classes;

    public Data(String filePath, String delimiter, int classColumn) {
        try {
            DataImporter importedData = new DataImporter(filePath);
            DataFormatter formatter = new DataFormatter(importedData.getLines());
            formatter.setDelimiter(delimiter);
            formatter.format();
            classes = formatter.extractClassColumn(classColumn);
            formatter.parseToDouble();
            data = formatter.getColumnsDouble();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ArrayList<Double>> getData() {
        return data;
    }

    public ArrayList<String> getClasses() {
        return classes;
    }
}
