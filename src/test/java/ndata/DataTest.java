package ndata;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * @author Jaroslaw Janas
 * @date 05/12/20
 * @project NN-Perceptron
 */
public class DataTest {
    String filePath = "src/test/java/ndata/data.txt";
    String delimiter = "\t";
    int classColumn = 3;

    @Test
    public void newDataTest() {
        Data dataFile = new Data(filePath, delimiter, classColumn);
        ArrayList<ArrayList<Double>> data = dataFile.getData();
        ArrayList<String> classes = dataFile.getClasses();

        //data
        Assert.assertEquals(9 ,data.size()); //columns
        Assert.assertEquals(6, data.get(0).size()); //length of columns
        Assert.assertEquals(63.03285714, data.get(8).get(5), 0.00000001); //last value in last column

        //classes
        Assert.assertEquals(6, classes.size());
        Assert.assertEquals("test", classes.get(5)); //last value
    }
}
