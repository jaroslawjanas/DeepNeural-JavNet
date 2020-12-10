package ndata;

import java.util.ArrayList;

/**
 * @author Jaroslaw Janas
 * @date 05/12/20
 * @project NN-Perceptron
 */
public class DataFile {
    private ArrayList<ArrayList<Double>> data;
    private ArrayList<String> classes;

    public DataFile(String filePath, String delimiter, int classColumn) {
        try {
//            import
            DataImporter importedData = new DataImporter(filePath);
            DataFormatter formatter = new DataFormatter(importedData.getLines());
//            separate with delimiter
            formatter.setDelimiter(delimiter);
//            format (flips "data" i.e. makes rows into columns)
            formatter.format();
//            extracts the class column
            classes = formatter.extractClassColumn(classColumn);
//            parse remaining numeric data to doubles (from Strings)
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