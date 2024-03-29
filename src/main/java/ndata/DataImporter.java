package ndata;

import java.io.*;
import java.util.ArrayList;

/**
 * @author Jaroslaw Janas
 * @date 04/12/20
 * @project NN-Perceptron
 */
public class DataImporter {
    ArrayList<String> lines = new ArrayList<>();

     DataImporter(String filePath) {
        FileReader fr = null;
        try {
            fr = new FileReader(filePath);

            BufferedReader br = new BufferedReader(fr);

            String line = br.readLine();
            while (line != null && line != "\n") {
                lines.add(line);
                line = br.readLine();
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        for (String line : lines) {
            str.append(line);
            str.append("\n");
        }
        return str.toString();
    }

    public ArrayList<String> getLines() {
        return lines;
    }

}
