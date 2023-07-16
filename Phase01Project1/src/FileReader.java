import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class FileReader {
    private static final String dataSetPath= "/Phase01Project1/SoftwareBooksDataset";
    public static Map<String,String> getFileDataWithFileName() {
        Map<String ,String> books = new HashMap<>();
        try {
            Path path = Path.of("").toAbsolutePath();
            File directoryPath = new File(path+dataSetPath);
            File[] filesList = directoryPath.listFiles();
            assert filesList != null;
            for(File file : filesList) {
                StringBuilder text = new StringBuilder();
                Scanner myReader = new Scanner(file);
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    text.append(data).append('\n');
                }
                myReader.close();
                books.put(file.getName().substring(0,file.getName().length()-4), text.toString());
            }
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return books;
    }
}
