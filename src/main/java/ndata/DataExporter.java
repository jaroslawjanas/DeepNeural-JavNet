package ndata;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Jaroslaw Janas
 * @date 09/12/20
 * @project NN-Perceptron
 */
public class DataExporter {
    String path;
    String filename;

    public DataExporter(String path, String filename) {
        this.path = path;
        this.filename = filename;
    }

//    https://stackoverflow.com/questions/28947250/create-a-directory-if-it-does-not-exist-and-then-create-the-files-in-that-direct/28948104
    public void write (String data) {
        File directory = new File(path);
        if (! directory.exists()){
            directory.mkdir();
            // If you require it to make the entire directory path including parents,
            // use directory.mkdirs(); here instead.
        }

        File file = new File(path + "/" + filename);
        try{
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(data);
            bw.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
//    --------------------
}
