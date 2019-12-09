import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class MainApp {
    final static String dir = "data";
    final static String fileName = "contacts.txt";

    public static void main (String[] args){
        Path path = Paths.get(dir, fileName);
        Path direc = Paths.get(dir);

        //define the list
        List<String> list = null;
        ArrayList <String> names = new ArrayList<>();
        ArrayList <String> numbers = new ArrayList<>();


        if (!Files.exists(path)){
            try {
                Files.createDirectories(direc);
                Files.createFile(path);
            }
            catch (IOException ex){
                ex.printStackTrace();
            }

        }
        else {
            try {
                list = Files.readAllLines(path);

                for (String eachline : list){
                    String [] each = eachline.split(": ");
                    names.add(each[0]);
                    numbers.add(each[1]);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
