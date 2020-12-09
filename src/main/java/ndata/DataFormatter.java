package ndata;

import java.util.ArrayList;

/**
 * @author Jaroslaw Janas
 * @date 04/12/20
 * @project NN-Perceptron
 */

public class DataFormatter {

    private ArrayList<String> lines;
    private String delimiter;
    private final ArrayList<ArrayList<String>> columns = new ArrayList<>();
    private ArrayList<ArrayList<Double>> columnsDouble;

    DataFormatter(ArrayList<String> lines) {
        this.lines = lines;
    }

//    splits based on delimiter and creates ArrayList of lines
    public void format() {
        //init arraylists - create columns based on the first line
        String[] firstLineElements = lines.get(0).split(delimiter);
        for (String element : firstLineElements) {
            columns.add(new ArrayList<>());
        }

//        go through all lines
        for (String line : lines) {

//            split
            String[] lineElements = line.split(delimiter);

//            push into respective columns
            int i = 0;
            for (String element : lineElements) {
                columns.get(i).add(element);
                i++;
            }
        }
    }

//    extracts/removes the class column from data
    public ArrayList<String> extractClassColumn(int i) {
        return columns.remove(i);
    }

//    converts a string to a number
    private Double toDouble(String s) {
        try {
            Double d = Double.parseDouble(s);
            return d;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public void parseToDouble() throws Exception {
//        initialize the ArrayList for doubles
        columnsDouble = new ArrayList<>();
//        go through each String column
        for (ArrayList<String> column : columns) {
//            create a new double column
            ArrayList<Double> newColumn = new ArrayList<>();
//            for each element in the string column
            for (String element : column) {
//                convert to a numeric type (Double)
                Double d = toDouble(element);
//                if failed to convert throw Exception
                if (d == null) {
                    throw new Exception("Data could not be converted to a numeric type");
                }
//                add converted element to new column
                newColumn.add(d);
            }
//            after iterating through all elements in the String column
//            add the newly created Double column to the doubleColumns ArrayList
            columnsDouble.add(newColumn);
        }
    }

    public ArrayList<ArrayList<Double>> getColumnsDouble() {
        return columnsDouble;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }
}