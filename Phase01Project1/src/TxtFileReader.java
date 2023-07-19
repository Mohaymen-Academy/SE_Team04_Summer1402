import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class TxtFileReader implements FileReader{
    @Override
    public Document readFile(String filePath) {
         Book book = null;
        try {
            File file = new File(filePath);
            StringBuilder text = new StringBuilder();
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                text.append(data).append('\n');
            }
            myReader.close();
            book = new Book(file.getName(),text.toString());
        } catch (Exception e) {
            System.out.println("Interrupted file");
            e.printStackTrace();
        }
        return book;
    }

    @Override
    public ArrayList<Document> readFiles(String folderPath) {
        ArrayList<Document> documents = new ArrayList<>();
        try {
            File directoryPath = new File(folderPath);
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
                documents.add(new Book(file.getName().substring(0,file.getName().length()-4),text.toString()));
            }
        } catch (Exception e) {
            System.out.println("Interrupted file");
            e.printStackTrace();
        }
        return documents;
    }
}
